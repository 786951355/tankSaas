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
public class DateQueryFragment extends BaseFragment implements DatePickerController {
    private String startDate;
    private String endDate;

    public static DateQueryFragment getInstance() {
        return new DateQueryFragment();
    }

    @Override
    protected void initView(View view) {
        DayPickerView dayPickerView = view.findViewById(R.id.pickerView);
        dayPickerView.setController(this);
        initDate();
        view.findViewById(R.id.bt_commit).setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("startDate", startDate);
            bundle.putString("endDate", endDate);
            navigationTo(new Intent(mContext, QueryResultActivity.class)
                    .putExtra("data", bundle));
        });
    }

    private void initDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date();
        startDate = sdf.format(date);
        endDate = startDate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ordersmodule_fragment_date_query;
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
