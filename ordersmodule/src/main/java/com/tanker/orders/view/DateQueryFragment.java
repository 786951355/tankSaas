package com.tanker.orders.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tanker.basemodule.base.BaseFragment;
import com.tanker.basemodule.utils.DateUtils;
import com.tanker.orders.R;
import com.tanker.resmodule.adpter.SimpleMonthAdapter;
import com.tanker.resmodule.widget.DatePickerController;
import com.tanker.resmodule.widget.DayPickerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author zhanglei
 * date 2018/4/28 14:49
 * description 日期搜索界面
 */
public class DateQueryFragment extends BaseFragment  {


    public static DateQueryFragment getInstance() {
        return new DateQueryFragment();
    }

    @Override
    protected void initView(View view) {

    }



    @Override
    protected int getLayoutId() {
        return R.layout.ordersmodule_fragment_date_query;
    }

}
