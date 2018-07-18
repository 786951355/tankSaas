package com.tanker.homemodule.view;

import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.homemodule.R;

/**
* @author lwj
* @ClassName: AddBillActivity
* @Description:  账本添加界面
* @date 2018/7/17 16:19
*/
public class AddBillActivity extends BaseActivity {

    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
       return R.layout.homemodule_activity_bill_add;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(mContext.getString(R.string.homemodule_bill_add_title_str));
    }
}
