package com.tanker.resmodule.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;

import com.tanker.basemodule.utils.DensityUtils;
import com.tanker.resmodule.R;
import com.tanker.resmodule.adpter.SimpleMonthAdapter;
import com.tanker.resmodule.utils.CalendarUtils;


import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * 日历
 */
public class SimpleMonthView extends View {

    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_DAY = "day";
    public static final String VIEW_PARAMS_MAX_YEAR = "max_year";
    public static final String VIEW_PARAMS_MAX_MONTH = "max_month";
    public static final String VIEW_PARAMS_MAX_DAY = "max_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DAY = "selected_begin_day";
    public static final String VIEW_PARAMS_SELECTED_LAST_DAY = "selected_last_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_MONTH = "selected_begin_month";
    public static final String VIEW_PARAMS_SELECTED_LAST_MONTH = "selected_last_month";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_YEAR = "selected_begin_year";
    public static final String VIEW_PARAMS_SELECTED_LAST_YEAR = "selected_last_year";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static int DAY_SELECTED_CIRCLE_SIZE;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE;
    protected static int MONTH_HEADER_SIZE;
    protected static int MONTH_LABEL_TEXT_SIZE;

    protected int mPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitleBGPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected int mCurrentDayTextColor;
    protected int mMonthTextColor;
    protected int mDayTextColor;
    protected int mDayNumColor;
    protected int mDayAbNumColor;
    protected int mMonthTitleBGColor;
    protected int mSelectedDaysColor;
    protected int mSelectedDaysBgColor;
    protected int mRangeSelectedDaysBgColor;
    protected int mRangeSelectedDaysColor;

    protected boolean mHasToday = false;
    protected boolean mIsPrev = false;
    protected int mSelectedBeginDay = -1;
    protected int mSelectedLastDay = -1;
    protected int mSelectedBeginMonth = -1;
    protected int mSelectedLastMonth = -1;
    protected int mSelectedBeginYear = -1;
    protected int mSelectedLastYear = -1;
    protected int mToday = -1;
    protected int mWeekStart = 1;
    protected int mNumDays = 7;
    protected int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    protected int mMonth;
    protected Boolean mDrawRect;
    protected int mRowHeight;
    protected int mWidth;
    protected int mYear;
    protected int mDay;
    protected int mMaxYear;
    protected int mMaxMonth;
    protected int mMaxDay;
    final Time today;
    private Date maxDate;

    private int mNumRows = DEFAULT_NUM_ROWS;
    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;
    private final Boolean isPrevDayEnabled;
    private final Boolean isLaterEnabled;
    private OnDayClickListener mOnDayClickListener;

    private Resources resources;
    private boolean hasWaterMask = true;
    private String[] weekLabels;

    public SimpleMonthView(Context context, TypedArray typedArray) {
        super(context);

        resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        weekLabels = resources.getStringArray(R.array.ordersmodule_calendar_week_label);
        mDayOfWeekTypeface = resources.getString(R.string.sans_serif);
        mMonthTitleTypeface = resources.getString(R.string.sans_serif);
        mCurrentDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorCurrentDay, resources.getColor(android.R.color.black));
        mMonthTextColor = typedArray.getColor(R.styleable.DayPickerView_colorMonthName, resources.getColor(android.R.color.black));
        mDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(android.R.color.black));
        mDayNumColor = typedArray.getColor(R.styleable.DayPickerView_colorNormalDay, resources.getColor(android.R.color.black));
        mDayAbNumColor = typedArray.getColor(R.styleable.DayPickerView_colorAbnormalDay, resources.getColor(android.R.color.black));
        mSelectedDaysColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayText, resources.getColor(android.R.color.holo_blue_dark));
        mRangeSelectedDaysColor = typedArray.getColor(R.styleable.DayPickerView_colorRangeSelectedDayText, resources.getColor(android.R.color.holo_blue_dark));
        mSelectedDaysBgColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayBackground, resources.getColor(R.color.colorAccent));
        mRangeSelectedDaysBgColor = typedArray.getColor(R.styleable.DayPickerView_colorRangeSelectedDayBackground, resources.getColor(R.color.colorAccent));
        mMonthTitleBGColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayText, resources.getColor(android.R.color.black));

        mDrawRect = typedArray.getBoolean(R.styleable.DayPickerView_drawRoundRect, false);

        MINI_DAY_NUMBER_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDay, resources.getDimensionPixelSize(R.dimen.ordersmodule_text_size_day));
        MONTH_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeMonth, resources.getDimensionPixelSize(R.dimen.ordersmodule_text_size_month));
        MONTH_DAY_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDayName, resources.getDimensionPixelSize(R.dimen.ordersmodule_text_size_day_name));
        MONTH_HEADER_SIZE = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_headerMonthHeight, resources.getDimensionPixelOffset(R.dimen.ordersmodule_header_month_height));
        DAY_SELECTED_CIRCLE_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_selectedDayRadius, resources.getDimensionPixelOffset(R.dimen.ordersmodule_selected_day_radius));

        mRowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DayPickerView_calendarHeight, resources.getDimensionPixelOffset(R.dimen.ordersmodule_calendar_height)) - MONTH_HEADER_SIZE) / 6);

        isPrevDayEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enablePreviousDay, true);
        isLaterEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enableLaterDay, true);

        initView();

        setBackground(ContextCompat.getDrawable(context, R.drawable.ordersmodule_calendar_bg_white));
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(weekLabels[i], x, y, mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int y = (MONTH_HEADER_SIZE - MONTH_DAY_LABEL_TEXT_SIZE) / 2 + (MONTH_LABEL_TEXT_SIZE / 3);
        canvas.drawText(getMonthAndYearString(), DensityUtils.dip2px(8), y, mMonthTitlePaint);
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    private String getMonthAndYearString() {
        return String.format(Locale.CHINA, "%d年%s", mYear, getZhMonth(mMonth));
    }

    private void onDayClick(SimpleMonthAdapter.CalendarDay calendarDay) {
        if (mOnDayClickListener != null && (isPrevDayEnabled || !((calendarDay.month == today.month) && (calendarDay.year == today.year) && calendarDay.day < today.monthDay))) {
            mOnDayClickListener.onDayClick(this, calendarDay);
        }
    }

    private boolean sameDay(int monthDay, Time time) {
        return (mYear == time.year) && (mMonth == time.month) && (monthDay == time.monthDay);
    }

    private boolean prevDay(int monthDay, Time time) {
        return ((mYear < time.year)) || (mYear == time.year && mMonth < time.month) || (mMonth == time.month && monthDay < time.monthDay);
    }

    private void drawSelectedBg(Canvas canvas, int x, int y, int color) {
        mSelectedCirclePaint.setColor(color);
        if (mDrawRect) {
            RectF rectF = new RectF(x - DAY_SELECTED_CIRCLE_SIZE, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_CIRCLE_SIZE, x + DAY_SELECTED_CIRCLE_SIZE, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_CIRCLE_SIZE);
            canvas.drawRoundRect(rectF, 10.0f, 10.0f, mSelectedCirclePaint);
        } else
            canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_CIRCLE_SIZE, mSelectedCirclePaint);
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2 - DAY_SEPARATOR_WIDTH + MONTH_HEADER_SIZE;
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;

            if (mHasToday && mDay == day && mSelectedLastYear == mSelectedBeginYear && mSelectedLastMonth == mSelectedBeginMonth && mSelectedLastDay == mSelectedBeginDay) { //画今天
//                mMonthNumPaint.setColor(mCurrentDayTextColor);
//                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else if (!isLaterEnabled && mHasToday && day > mMaxDay) { //画最大日期之后的时间
                mMonthNumPaint.setColor(mDayAbNumColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else if (!isPrevDayEnabled && prevDay(day, today) && today.month == mMonth && today.year == mYear) {
                mMonthNumPaint.setColor(mDayAbNumColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            } else {//画其他时间
                mMonthNumPaint.setColor(mDayNumColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            if ((mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear)
                    || (mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear)) {
                drawSelectedBg(canvas, x, y, mSelectedDaysBgColor);
                mMonthNumPaint.setColor(mSelectedDaysColor);
            } else if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 //前提条件
                    && ((upToDown() && (upToDownOneMonth(day) || upToDownOverMonth(day) || upToDownOverYear(day)))
                    || (!upToDown() && (downToUpOneMonth(day) || downToUpOverMonth(day) || downToUpOverYear(day)))))) {//画选中日期的背景
                drawSelectedBg(canvas, x, y, mRangeSelectedDaysBgColor);
                mMonthNumPaint.setColor(mRangeSelectedDaysColor);
            }

            canvas.drawText(String.valueOf(day), x, y, mMonthNumPaint);

            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    private boolean sameYear() {
        return mYear == mSelectedBeginYear && mYear == mSelectedLastYear;
    }

    private boolean sameMonth() {
        return mMonth == mSelectedBeginMonth && mMonth == mSelectedLastMonth;
    }

    private boolean upToDown() {
        return mSelectedBeginYear < mSelectedLastYear
                || (mSelectedBeginYear == mSelectedLastYear && mSelectedBeginMonth < mSelectedLastMonth)
                || (mSelectedBeginYear == mSelectedLastYear && mSelectedBeginMonth == mSelectedLastMonth && mSelectedBeginDay < mSelectedLastDay);
    }

    private boolean upToDownOneMonth(int day) { //自下往上年内月内选择日期
        return sameYear() && sameMonth() && day <= mSelectedLastDay && day >= mSelectedBeginDay;
    }

    private boolean upToDownOverMonth(int day) { //自下往上年内跨月选择日期
        return sameYear() && mSelectedLastMonth != mSelectedBeginMonth && ((mMonth == mSelectedBeginMonth && day >= mSelectedBeginDay)
                || (mMonth == mSelectedLastMonth && day <= mSelectedLastDay)
                || (mMonth > mSelectedBeginMonth && mMonth < mSelectedLastMonth));
    }

    private boolean upToDownOverYear(int day) { //自下往上跨年选择日期
        return mSelectedLastYear != mSelectedBeginYear && ((mYear == mSelectedBeginYear && ((mMonth == mSelectedBeginMonth && day >= mSelectedBeginDay) || mMonth > mSelectedBeginMonth))
                || (mYear == mSelectedLastYear && ((mMonth == mSelectedLastMonth && day <= mSelectedLastDay) || (mMonth < mSelectedLastMonth)))
                || (mYear > mSelectedBeginYear && mYear < mSelectedLastYear));
    }

    private boolean downToUpOneMonth(int day) { //自下往上年内月内选择日期
        return sameYear() && sameMonth() && day <= mSelectedBeginDay && day >= mSelectedLastDay;
    }

    private boolean downToUpOverMonth(int day) { //自下往上年内跨月选择日期
        return sameYear() && mSelectedLastMonth != mSelectedBeginMonth && ((mMonth == mSelectedBeginMonth && day <= mSelectedBeginDay)
                || (mMonth == mSelectedLastMonth && day >= mSelectedLastDay)
                || (mMonth < mSelectedBeginMonth && mMonth > mSelectedLastMonth));
    }

    private boolean downToUpOverYear(int day) { //自下往上跨年选择日期
        return mSelectedLastYear != mSelectedBeginYear && ((mYear == mSelectedBeginYear && ((mMonth == mSelectedBeginMonth && day <= mSelectedBeginDay) || mMonth < mSelectedBeginMonth))
                || (mYear == mSelectedLastYear && ((mMonth == mSelectedLastMonth && day >= mSelectedLastDay) || (mMonth > mSelectedLastMonth)))
                || (mYear < mSelectedBeginYear && mYear > mSelectedLastYear));
    }

    public void drawWaterMask() {
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#979797"));
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        paint.setTextSize(108);
        paint.setFakeBoldText(true);
        String month = getZhMonth(mMonth);
        float textWidth = paint.measureText(month);
        canvas.drawText(month, getMeasuredWidth() / 2 - textWidth / 2, getMeasuredHeight() / 2 + textWidth / 2, paint);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
        bitmapDrawable.setDither(true);

        setBackground(bitmapDrawable);
    }

    public SimpleMonthAdapter.CalendarDay getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }

        int yDay = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mMonth, mYear) < day || day < 1)
            return null;

        return new SimpleMonthAdapter.CalendarDay(mYear, mMonth, day);
    }

    public String getZhMonth(int month) {
        if (month < 0 || month > 11) {
            throw new IllegalArgumentException("Invalid Month");
        }
        return resources.getStringArray(R.array.ordersmodule_calendar_zh_month_label)[month];
    }

    protected void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_LABEL_TEXT_SIZE);
        mMonthTitlePaint.setColor(mMonthTextColor);
        mMonthTitlePaint.setStyle(Style.FILL);

        mMonthTitleBGPaint = new Paint();
        mMonthTitleBGPaint.setFakeBoldText(true);
        mMonthTitleBGPaint.setAntiAlias(true);
        mMonthTitleBGPaint.setColor(mMonthTitleBGColor);
        mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        mMonthTitleBGPaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);
        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);

    }

    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
        if (hasWaterMask) {
            drawWaterMask();
            hasWaterMask = false;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + MONTH_HEADER_SIZE);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            SimpleMonthAdapter.CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null && (isLaterEnabled || calendarDay.getDate().getTime() - maxDate.getTime() < 24 * 3600 * 1000)) {
                onDayClick(calendarDay);
            }
        }
        return true;
    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
        requestLayout();
    }

    @SuppressLint("WrongConstant")
    public void setMonthParams(HashMap<String, Integer> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR) && !params.containsKey(VIEW_PARAMS_DAY)
                && !params.containsKey(VIEW_PARAMS_MAX_MONTH) && !params.containsKey(VIEW_PARAMS_MAX_YEAR) && !params.containsKey(VIEW_PARAMS_MAX_DAY)) {
            throw new InvalidParameterException("You must specify day, month and year for this view");
        }
        setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DAY)) {
            mSelectedBeginDay = params.get(VIEW_PARAMS_SELECTED_BEGIN_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DAY)) {
            mSelectedLastDay = params.get(VIEW_PARAMS_SELECTED_LAST_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_MONTH)) {
            mSelectedBeginMonth = params.get(VIEW_PARAMS_SELECTED_BEGIN_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_MONTH)) {
            mSelectedLastMonth = params.get(VIEW_PARAMS_SELECTED_LAST_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_YEAR)) {
            mSelectedBeginYear = params.get(VIEW_PARAMS_SELECTED_BEGIN_YEAR);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_YEAR)) {
            mSelectedLastYear = params.get(VIEW_PARAMS_SELECTED_LAST_YEAR);
        }

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);
        mDay = params.get(VIEW_PARAMS_DAY);
        mMaxYear = params.get(VIEW_PARAMS_MAX_YEAR);
        mMaxMonth = params.get(VIEW_PARAMS_MAX_MONTH);
        mMaxDay = params.get(VIEW_PARAMS_MAX_DAY);

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        hasWaterMask = true;

        mNumCells = CalendarUtils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }

            mIsPrev = prevDay(day, today);
        }

        mNumRows = calculateNumRows();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, SimpleMonthAdapter.CalendarDay calendarDay);
    }
}