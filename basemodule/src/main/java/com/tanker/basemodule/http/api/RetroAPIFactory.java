package com.tanker.basemodule.http.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.BuildConfig;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.https.certification.TrustAllCerts;
import com.tanker.basemodule.utils.NetUtil;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ronny on 2016/7/6.
 */
public class RetroAPIFactory {

    //设缓存有效期为1天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    public static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN =
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder().addHeader("token", SaasApp.getInstance().getToken()).build();
            if (!NetUtil.isNetworkAvailable(SaasApp.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(SaasApp.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                Map<String, List<String>> stringListMap = originalResponse.headers().toMultimap();
                List<String> versionCode = stringListMap.get(AppConstants.ANDROID_VERISON);
                if (versionCode != null && versionCode.size() > 0) {
                    Integer integer = Integer.valueOf(versionCode.get(0));
                    if (integer > SaasApp.getInstance().getVersionCode()) {
                        Beta.checkUpgrade(false, false);
                    }
                }

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
//    public static final String BASEURL = BuildConfig.HostUrl;
//    public static final String BASEURL = "http://10.0.70.7:8080";

    private static Retrofit innerRetrofit;
    private static Retrofit outerRetrofit;

    private RetroAPIFactory() {
        throw new AssertionError();
    }

    public static String BASEURL = BuildConfig.isDebug ? Hawk.get(AppConstants.HAWK_APP_HOST, "https://test.carrierapi.tankchaoren.com") : BuildConfig.HostUrl;

    public static <T> T create(final Class<T> service) {
        return innerRetrofit.create(service);
    }


    /**
     * 用来解决https需要证书认证的问题
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    /**
     * 初始化网络通信服务
     */
    public static void init() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(SaasApp.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        mResponseLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sRewriteCacheControlInterceptor)
//                .addInterceptor(mRequestLogInterceptor)
                .addInterceptor(mResponseLogInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
//                        return "test.image.tankchaoren.com".equals(s)
//                                || "test.carrierapi.tankchaoren.com".equals(s);
                    }
                })
                .build();


        innerRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build();

        outerRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build();
    }


    private static final HttpLoggingInterceptor mResponseLogInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            if (message.contains("http") && !message.contains("ms")) {
                Logger.d("\nsglei-net请求地址：" + message);
            } else if (message.contains(AppConstants.PARAM_SIGN)) {
                Logger.d("\nsglei-net请求报文：" + message);
            } else if (message.contains(AppConstants.PARAM_DATA)) {
                Logger.d("\nsglei-net响应报文：" + message);
            }
        }
    });

}
