package com.tanker.resmodule.widget;



import com.tanker.resmodule.adpter.SimpleMonthAdapter;

import java.util.Date;

/**
 * 日历
 */
public interface DatePickerController {
	Date getMaxDate();

	Date getMinDate();

	void onDayOfMonthSelected(int year, int month, int day);

    void onDateRangeSelected(final SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays);

}