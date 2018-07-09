package com.tanker.basemodule.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.tanker.basemodule.R;
import com.tanker.basemodule.common.TankerApp;


public class TAimationUtils {
    public static void startLoadingAnimation(ImageView imageView) {
        imageView.setBackground(null);
        imageView.setImageResource(R.drawable.progress);
        imageView.refreshDrawableState();
        Animation circle_anim = AnimationUtils.loadAnimation(TankerApp.getInstance(), R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            imageView.startAnimation(circle_anim);  //开始动画
        }
    }

    public static void cancleAnimation(View view) {
        view.clearAnimation();
//        view.setBackgroundResource(R.drawable.dash_line_shape);
    }



    /**
     *  旋转的属性动画
     * @param view
     * @param isClockwise 是否是顺时针
     */
    public static void startRotationAnim(View view,boolean isClockwise) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0f, isClockwise?180f:-180f);
        // 动画的持续时间，执行多久？
        anim.setDuration(300);
        // 正式开始启动执行动画
        anim.start();
    }

}
