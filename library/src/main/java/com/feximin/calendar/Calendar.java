package com.feximin.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Neo on 16/5/14.
 */
public class Calendar extends LinearLayout {

    private int mTitleColor = 0xFF555555;
    private int mTitleSize;
    private int mWeekColor = 0xFF888888;
    private int mWeekSize;
    private boolean mShowTitle;
    private boolean mShowWeek;
    private boolean mShowDivider;
    private int mDividerColor = 0xFF777777;
    private int mDividerWidth;

    private TextView mTvTitle;
    private LinearLayout mWeekContainer;
    private LinearLayout mContentContainer;

    private final String[] mWeekNames = {"星期-", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    private int mCurMonth;
    private int mCurYear;
    private int mCurDay;
    public Calendar(Context context) {
        this(context, null);
    }

    public Calendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        mTitleSize = Tool.spToPx(context, 15);
        mWeekSize = Tool.spToPx(context, 11);
        mDividerWidth = Tool.dpToPx(context, 1);
        String titleText = null;
        if (attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Calendar);
            mTitleSize = ta.getDimensionPixelSize(R.styleable.Calendar_title_text_size, mTitleSize);
            mTitleColor = ta.getColor(R.styleable.Calendar_title_text_color, mTitleColor);
            titleText = ta.getString(R.styleable.Calendar_title_text);
            mWeekSize = ta.getDimensionPixelSize(R.styleable.Calendar_week_text_size, mWeekSize);
            mWeekColor = ta.getColor(R.styleable.Calendar_week_text_color, mWeekColor);
            mDividerColor = ta.getColor(R.styleable.Calendar_divider_color, mDividerColor);
            mDividerWidth = ta.getDimensionPixelSize(R.styleable.Calendar_divider_width, mDividerWidth);
            mShowTitle = ta.getBoolean(R.styleable.Calendar_show_title, true);
            mShowWeek = ta.getBoolean(R.styleable.Calendar_show_week, true);
            mShowDivider = ta.getBoolean(R.styleable.Calendar_show_divider, true);
            ta.recycle();
        }
        int dp5 = Tool.dpToPx(context, 5);
        int dp10 = Tool.dpToPx(context, 10);
        mTvTitle = new TextView(context);
        mTvTitle.setText(titleText);
        mTvTitle.setTextColor(mTitleColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTvTitle.setPadding(dp10, dp10, dp10, dp10);
        addView(mTvTitle);
        if (!mShowTitle) mTvTitle.setVisibility(GONE);

        mWeekContainer = new LinearLayout(context);
        mWeekContainer.setOrientation(HORIZONTAL);
        for (int i = 0; i < 7; i++){
            TextView tv = new TextView(context);
            tv.setPadding(0, dp5, 0, dp5);
            tv.setText(mWeekNames[i]);
            tv.setTextColor(mWeekColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mWeekColor);
            mWeekContainer.addView(tv, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
        if (!mShowWeek) mWeekContainer.setVisibility(GONE);

        mContentContainer = new LinearLayout(context);
        mContentContainer.setOrientation(VERTICAL);
        mContentContainer.setDividerDrawable(null);
    }




    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setTitleText(String text){

    }

    public void nextMonth(){
        mCurMonth ++;
        if (mCurMonth >= 12){
            mCurMonth = 0;
            mCurYear++;
        }
    }

    public void previousMonth(){
        mCurMonth --;
        if (mCurMonth < 0){
            mCurMonth = 11;
            mCurYear --;
            checkYear();
        }
    }

    public void nextYear(){
        mCurYear ++;
    }

    public void previousYear(){
        mCurYear --;
        checkYear();
    }

    private void checkYear(){
        if(mCurYear < 0){
            throw new IllegalArgumentException("year can not be negative !!");
        }
    }


    public void setMonth(int month){
        if (mCurMonth != month){
            mCurMonth = month;
        }
    }

    public void setYear(int year){
        if (year > 0 && mCurYear != year){
            mCurYear = year;
        }
    }

    public void setDay(int day){
        if (day > 0 && mCurDay != day){
            mCurDay = day;
        }
    }

    public void setTitleVisibility(int visibility){

    }

    public void setWeekTitleVisibility(int visibility){
    }

    public void addDecorView(View view , int day, boolean over){

    }

    public int getDayCount(){
        return Tool.getDayCount(mCurMonth);
    }

}
