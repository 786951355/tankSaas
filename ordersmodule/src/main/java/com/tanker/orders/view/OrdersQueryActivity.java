package com.tanker.orders.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tanker.basemodule.adapter.TabPagerAdapter;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.utils.DateUtils;
import com.tanker.orders.R;
import com.tanker.resmodule.adpter.SimpleMonthAdapter;
import com.tanker.resmodule.widget.DatePickerController;
import com.tanker.resmodule.widget.DayPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author ronnyluo
 * @date 2018/7/20 上午11:20
 * @desc 按日期查询订单
 */
public class OrdersQueryActivity extends BaseActivity implements DatePickerController {
    private String startDate;
    private String endDate;

    @Override
    protected void initView() {
        DayPickerView dayPickerView = findViewById(R.id.pickerView);
        dayPickerView.setController(this);
        initDate();
    }

    private void initDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date();
        startDate = sdf.format(date);
        endDate = startDate;
    }

    @Override
    public int getContentView() {
        return R.layout.ordersmodule_activity_orders_query;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(getString(R.string.ordersmodule_title_quickly_query))
                .setRightText(getString(R.string.common_confirm))
                .setRightTextColor(getmColor(R.color.text_green))
                .setOnRightIconClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("startDate", startDate);
                    bundle.putString("endDate", endDate);
                    navigationTo(new Intent(mContext, QueryResultActivity.class)
                            .putExtra("data", bundle));
                });
    }



    @Override
    public Date getMaxDate() {
        return new Date();
    }

    @Override
    public Date getMinDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            String START_DATE = "2017-01-01";
            return sdf.parse(START_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        startDate = String.format(Locale.CHINA, "%d-%d-%d", year, month, day);
        endDate = startDate;
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        if (DateUtils.compareDate(selectedDays.getFirst().toString(), selectedDays.getLast().toString()) > 0) {
            startDate = selectedDays.getLast().toString();
            endDate = selectedDays.getFirst().toString();
        } else {
            startDate = selectedDays.getFirst().toString();
            endDate = selectedDays.getLast().toString();
        }
    }


}
