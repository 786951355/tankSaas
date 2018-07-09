package com.tanker.basemodule.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @author lwj
 * @ClassName: ShareUtils
 * @Description: 友盟分享工具类
 * @date 2018/6/11 17:44
 */
public class ShareUtils {
    private static final String TAG = "ShareUtils";

    /**
     * @author lwj
     * @describe 分享链接
     * @params
     * @time 2018/6/11 18:21
     */
    public static void shareWeb(final Activity activity, String webUrl, String title, String description, String imageUrl, int imageID, SHARE_MEDIA platform) {
        UMWeb web = new UMWeb(webUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        if (!TextUtils.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        } else if (imageID != 0) {
            web.setThumb(new UMImage(activity, imageID));  //本地缩略图
        }
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d(TAG, "分享开始");
                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    ShowMassageUtil.showToast("收藏成功");
                                } else {
                                    ShowMassageUtil.showToast("分享成功");
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowMassageUtil.showToast("分享失败");
                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowMassageUtil.showToast("分享取消");
                            }
                        });
                    }
                })
                .share();
    }

}
