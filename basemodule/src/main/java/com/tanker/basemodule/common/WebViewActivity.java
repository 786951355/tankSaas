package com.tanker.basemodule.common;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.utils.ShareUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * Created by :Ronny
 * Date on :2018/3/23 22:03
 */

public class WebViewActivity extends BaseActivity {
    public static final int TO_PROTOCOL = 0;
    public static final int TO_INVOICE = 1;
    public static final int TO_ADDRESS = 2;
    public static final int TO_AUTH_BOOK = 3;
    //通知
    public static final int TO_NOTICE = 4;
    int action;

    private ProgressBar progressBar;
    WebView webview;
    private String intentUrl = "http://www.zhaoguanche.com/";

    //通知id
    private String noticeId = "";
    //通知标题
    private String noticeTitle = "";
    //通知描述
    private String noticeDescript = "";
    //通知网页链接
    private String noticeWebUrl = "";
    //通知图片链接
    private String noticeImageUrl;
    //通知本地图片
    private int noticeImageID;
    //通知已读状态
    private boolean isread;

    @Override
    public int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        Intent intent = getIntent();
        action = intent.getIntExtra(AppConstants.WEB_ACTION, -1);
        //通知id
        noticeId = intent.getStringExtra(AppConstants.NOTICE_ID);
        //通知标题
        noticeTitle = intent.getStringExtra(AppConstants.NOTICE_TITLE);
        //通知描述
        noticeDescript = intent.getStringExtra(AppConstants.NOTICE_DESCRIPTION);
        //通知网页链接
        noticeWebUrl = intent.getStringExtra(AppConstants.NOTICE_WEBURL);
        isread = intent.getBooleanExtra(AppConstants.ISREAD, true);
        String title = "";
        switch (action) {
            case TO_PROTOCOL:
                title = getString(R.string.protocol);
                intentUrl = getIntent().getStringExtra(AppConstants.WEB_URL);
                break;
            case TO_INVOICE:
                title = getString(R.string.invoice_info);
                intentUrl = SaasApp.getInstance().getConfigManager().getConfigInfo().getAppInvoiceSrc();
                break;
            case TO_ADDRESS:
                title = getString(R.string.address_info);
                intentUrl = SaasApp.getInstance().getConfigManager().getConfigInfo().getAppAddressSrc();
                break;
            case TO_NOTICE:
                title = "公告";
                //intentUrl = AppConstants.PROTOCOL_URL;
                intentUrl = noticeWebUrl;

                rToolbar.setRightIcon(R.drawable.icon_share).setOnRightIconClickListener(view -> {
                    //友盟分享
                    new ShareAction(mContext)
                            .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)//可分享的市场
                            .setShareboardclickCallback((snsPlatform, share_media) -> ShareUtils.shareWeb(mContext, noticeWebUrl, noticeTitle, noticeDescript, noticeImageUrl, noticeImageID, share_media)).open();
                });
                break;
            case -1:
                showMessage("打开web页面失败");
                finish();

                return;
        }
        if (intentUrl == null) {
            showMessage("打开web页面失败");
            finish();
            return;
        }
        rToolbar.setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initView() {
        //友盟禁止默认的页面统计功能
        MobclickAgent.openActivityDurationTrack(false);
        progressBar = findViewById(R.id.progressBar);
        webview = findViewById(R.id.webview);
        webview.setVerticalScrollBarEnabled(false); //垂直滚动不显示
        WebSettings settings = webview.getSettings();
        settings.setSupportZoom(true);
        //设置支持js
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.loadUrl(intentUrl);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (action) {
            case TO_INVOICE://开票信息
                //手动统计页面(页面名称可自定义)
                MobclickAgent.onPageStart("开票资料界面");
                break;
            case TO_ADDRESS://邮寄地址
                //手动统计页面(页面名称可自定义)
                MobclickAgent.onPageStart("地址信息界面");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        switch (action) {
            case TO_INVOICE://开票信息
                //手动统计页面(页面名称可自定义)
                MobclickAgent.onPageEnd("开票资料界面");
                break;
            case TO_ADDRESS://邮寄地址
                //手动统计页面(页面名称可自定义)
                MobclickAgent.onPageEnd("地址信息界面");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
