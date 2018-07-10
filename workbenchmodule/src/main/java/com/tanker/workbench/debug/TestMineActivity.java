package com.tanker.workbench.debug;

import android.support.v4.app.FragmentTransaction;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.model.login_model.UserInfo;
import com.tanker.workbench.R;
import com.tanker.workbench.view.WorkbenchFragment;

/**
 * Created by ronny on 2018/3/23.
 */

public class TestMineActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_test;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        UserInfo user = new UserInfo();
        user.setUserId("2");
        SaasApp.getInstance().getUserManager().setUser(user);
        rToolbar.setTitle("Mine界面测试入口")
                .setToolbarVisible(false);
    }

    @Override
    protected void initView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.fl_mine, new WorkbenchFragment());
        fragmentTransaction.commit();
    }

}
