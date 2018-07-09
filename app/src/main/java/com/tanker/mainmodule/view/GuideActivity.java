package com.tanker.mainmodule.view;

import android.view.View;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.widget.CycleViewPager;
import com.tanker.basemodule.widget.ViewFactory;
import com.tanker.mainmodule.R;
import com.tanker.basemodule.router.ReflectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by zhanglei 2018/4/18
 * description: 引导页 第一次安装的时候启动
 */
public class GuideActivity extends BaseActivity implements CycleViewPager.ImageCycleViewListener {
    private List<View> views = new ArrayList<>();
    private List<Integer> lines = Arrays.asList(R.drawable.guide_line_one, R.drawable.guide_line_two,
            R.drawable.guide_line_three, R.drawable.guide_line_four);
    private List<Integer> icons = Arrays.asList(R.drawable.guide_icon_one, R.drawable.guide_icon_two,
            R.drawable.guide_icon_three, R.drawable.guide_icon_four);
    private String[] texts;

    @Override
    protected void initView() {
        initPageView();
    }

    private void initPageView() {
        texts = getResources().getStringArray(R.array.guide_texts);
        CycleViewPager cycleViewPager = (CycleViewPager) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager);
        for (int i = 0; i < texts.length; i++) {
            views.add(ViewFactory.getView(mContext, lines.get(i), icons.get(i), texts[i], texts.length - 1 == i, new ViewFactory.ClickListener() {
                @Override
                public void click() {
                    ReflectUtils.startActivityWithName(GuideActivity.this,
                            "com.tanker.loginmodule.view.TankerLoginActivity");
                    GuideActivity.this.finish();
                }
            }));
        }
        cycleViewPager.setCycle(false);
        cycleViewPager.setData(views, this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(false);
    }

    @Override
    public void onImageClick(int position, View imageView) {

    }
}
