package com.tanker.homemodule.debug;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.model.login_model.UserInfo;
import com.tanker.homemodule.R;

public class TestOrderActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.ordersmodule_activity_order_test;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        UserInfo user = new UserInfo();
        user.setUserId("291");
        TankerApp.getInstance().getUserManager().setUser(user);
        rToolbar.setTitle("订单管理").setToolbarVisible(true);
    }

    @Override
    protected void initView() {
        /*findViewById(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestOrderActivity.this, ObjectionActivity.class);
                //type：提交异议类型节点 1.到达装货点 2.装货 3.到达卸货点 4.卸货
                intent.putExtra(HomeConstants.OBJECT_TYPY, "1");
                intent.putExtra("carrierOrderId", "1000");
                navigationTo(intent);
            }
        });*/
    }

}
