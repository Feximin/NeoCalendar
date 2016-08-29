package com.feximin.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Neo on 16/5/14.
 */
public class CalendarView extends LinearLayout {

    private int mTitleColor = 0xFF555555;
    private int mTitleSize;
    private int mWeekColor = 0xFF888888;
    private int mWeekSize;
    private boolean mShowTitle = true;
    private boolean mShowWeek = true;
    private boolean mShowDivider = true;
    private int mDividerColor = 0xFFCCCCCC;
    private int mDividerWidth;

    private float mItemRatio = 1;                 //每一天的内容的宽高比，默认是正方形的

    private TextView mTvTitle;
    private LinearLayout mWeekContainer;
    private LinearLayout mContentContainer;

    private final String[] mWeekNames = {"星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private int mCurMonth;
    private int mCurYear;
    private int mCurDay;

    private ShapeDrawable mDividerRow;
    private ShapeDrawable mDividerColumn;

    private Calendar mCurCalendar = Calendar.getInstance();

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        mTitleSize = Tool.spToPx(context, 15);
        mWeekSize = Tool.spToPx(context, 10);
        mDividerWidth = Tool.dpToPx(context, 1);
        String titleText = null;
        if (attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
            mTitleSize = ta.getDimensionPixelSize(R.styleable.CalendarView_title_text_size, mTitleSize);
            mTitleColor = ta.getColor(R.styleable.CalendarView_title_text_color, mTitleColor);
            titleText = ta.getString(R.styleable.CalendarView_title_text);
            mWeekSize = ta.getDimensionPixelSize(R.styleable.CalendarView_week_text_size, mWeekSize);
            mWeekColor = ta.getColor(R.styleable.CalendarView_week_text_color, mWeekColor);
            mDividerColor = ta.getColor(R.styleable.CalendarView_divider_color, mDividerColor);
            mDividerWidth = ta.getDimensionPixelSize(R.styleable.CalendarView_divider_width, mDividerWidth);
            mShowTitle = ta.getBoolean(R.styleable.CalendarView_show_title, true);
            mShowWeek = ta.getBoolean(R.styleable.CalendarView_show_week, true);
            mShowDivider = ta.getBoolean(R.styleable.CalendarView_show_divider, true);
            mItemRatio = ta.getFloat(R.styleable.CalendarView_item_ratio, 1);
            ta.recycle();
        }
        int dp5 = Tool.dpToPx(context, 5);
        int dp10 = Tool.dpToPx(context, 10);
        mTvTitle = new TextView(context);
        mTvTitle.setText(titleText);
        mTvTitle.setTextColor(mTitleColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTvTitle.setPadding(dp10, dp10, dp10, dp10);
        addView(mTvTitle, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (!mShowTitle) mTvTitle.setVisibility(GONE);

        mWeekContainer = new LinearLayout(context);
        mWeekContainer.setOrientation(HORIZONTAL);
        for (int i = 0; i < 7; i++){
            TextView tv = new TextView(context);
            tv.setPadding(0, dp5, 0, dp5);
            tv.setText(mWeekNames[i]);
            tv.setTextColor(mWeekColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mWeekSize);
            tv.setGravity(Gravity.CENTER);
            mWeekContainer.addView(tv, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        }
        addView(mWeekContainer, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (!mShowWeek) mWeekContainer.setVisibility(GONE);

        mContentContainer = new LinearLayout(context);
        mContentContainer.setOrientation(VERTICAL);
        addView(mContentContainer, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    protected View generateItemView(int day){
        TextView tv = new TextView(getContext());
        tv.setText(day + "");
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.selector_fff_f6);
        return tv;
    }

    public void showDivider(boolean b){
        if (b != mShowDivider){
            mShowDivider = b;
            refreshDivider(true);
        }
    }

    public void setDivider(int color, int width){
        if (mDividerColor != color && mDividerWidth != width){
            mDividerColor = color;
            mDividerWidth = width;
            mDividerRow = null;
            mDividerColumn = null;
            refreshDivider(false);
        }
    }

    private void refreshDivider(boolean force){
        boolean needRefresh = false;
        if (mShowDivider){
            if (mDividerRow == null || mDividerColumn == null){
                mDividerRow = new ShapeDrawable();
                mDividerRow.setIntrinsicHeight(mDividerWidth);
                mDividerRow.getPaint().setColor(mDividerColor);
                mDividerColumn = new ShapeDrawable();
                mDividerColumn.setIntrinsicWidth(mDividerWidth);
                mDividerColumn.getPaint().setColor(mDividerColor);
                needRefresh = true;
            }

        }else{
            if (mDividerRow != null || mDividerColumn != null) {
                mDividerRow = null;
                mDividerColumn = null;
                needRefresh = true;
            }
        }
        if (force || needRefresh){
            mContentContainer.setShowDividers(SHOW_DIVIDER_BEGINNING | SHOW_DIVIDER_MIDDLE | SHOW_DIVIDER_END);
            mContentContainer.setDividerDrawable(mDividerRow);
            int N = mContentContainer.getChildCount();
            for (int i = 0; i < N; i++) {
                LinearLayout ll = (LinearLayout) mContentContainer.getChildAt(i);
                ll.setShowDividers(SHOW_DIVIDER_BEGINNING | SHOW_DIVIDER_MIDDLE | SHOW_DIVIDER_END);
                ll.setDividerDrawable(mDividerColumn);
            }
        }
    }

    public void updateItem(int day, View view){
        if (view != null){
            RatioLinearLayout parent = getItemViewParent(day);
            if (parent != null){
                parent.removeAllViews();
                parent.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            }
        }
    }


    public void setTitleText(String text){
        mTvTitle.setText(text);
    }

    public void nextMonth(){
        mCurMonth ++;
        if (mCurMonth >= 12){
            mCurMonth = 0;
            mCurYear++;
        }
        refreshItem();
    }

    public void autoFillTitle(){
        String title = String.format("%s年%s月%s日", mCurYear, mCurMonth + 1, mCurDay);
        mTvTitle.setText(title);
    }

    public void previousMonth(){
        mCurMonth --;
        if (mCurMonth < 0){
            mCurMonth = 11;
            mCurYear --;
            checkYear();
        }
        refreshItem();
    }

    public void nextYear(){
        mCurYear ++;
        refreshItem();
    }


    public void previousYear(){
        mCurYear --;
        checkYear();
        refreshItem();
    }

    private void checkYear(){
        if(mCurYear < 0){
            throw new IllegalArgumentException("year can not be negative !!");
        }
    }

    public void nextDay(){
        mCurDay ++;
        int dayCount = Tool.getDayCount(mCurMonth);
        if (mCurDay > dayCount){
            mCurDay = 1;
            nextMonth();
        }else{
            setDate(mCurYear, mCurMonth, mCurDay);
        }
    }

    public void previousDay(){
        mCurDay --;
        if (mCurDay == 0){
            mCurDay = Tool.getDayCount(mCurMonth);
            previousMonth();
        }else{
            setDate(mCurYear, mCurMonth, mCurDay);
        }
    }

    protected void refreshItem(){
        int weekIndex = Tool.getWeekIndex(mCurYear, mCurMonth, 1);         //为什么要减1
        int dayCount = Tool.getDayCount(mCurMonth);
        int rowCount = (dayCount + weekIndex) / 7;
        int mod = (dayCount + weekIndex) % 7;
        if (mod > 0) rowCount ++;
        boolean forceRefreshDivider = false;
        int N = mContentContainer.getChildCount();
        if (N == 0){
            for (int i = 0; i < rowCount; i++){
                fillRow();
            }
            forceRefreshDivider = true;
        }else{
            if (N > rowCount){
                for (int i = N - 1; i>=rowCount; i--){
                    mContentContainer.removeViewAt(i);
                }
            }else if (N < rowCount){
                for (int i = N; i<rowCount; i++){
                    fillRow();
                }
                forceRefreshDivider = true;
            }
        }
        for (int i = 0; i < rowCount ; i++){
            LinearLayout row = (LinearLayout) mContentContainer.getChildAt(i);
            for (int j = 0; j < 7; j++){
                int index = i * 7 + j;
                RatioLinearLayout item = (RatioLinearLayout) row.getChildAt(j);
                if (item.getChildCount() > 0) item.removeAllViews();
                int day = index - weekIndex + 1;
                if (day > 0 && day <= dayCount){
                    View view = generateItemView(day);
                    if (view != null) {
                        view.setSelected(day == mCurDay);
                        item.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    }
                }
            }
        }

        refreshDivider(forceRefreshDivider);
    }

    private void fillRow(){
        Context context = getContext();
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(HORIZONTAL);
        for (int j = 0; j < 7; j++){
            RatioLinearLayout frame = new RatioLinearLayout(context);
            frame.setRatio(mItemRatio);
            frame.setGravity(Gravity.CENTER);
            ll.addView(frame, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        }
        mContentContainer.addView(ll, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public int[] getPosition(int day){
        int weekIndex = Tool.getWeekIndex(mCurYear, mCurMonth, 1);
        int[] position = new int[2];
        int row = (day + weekIndex) / 7;
        int column = (day + weekIndex) % 7;
        if (column > 0) {
            column --;
        }else{
            if (row > 0) row --;
            column = 6;
        }
        position[0] = row;
        position[1] = column;
        return position;
    }

    public RatioLinearLayout getItemViewParent(int day){
        int[] position = getPosition(day);
        LinearLayout row = (LinearLayout) mContentContainer.getChildAt(position[0]);
        if (row != null) {
            RatioLinearLayout item = (RatioLinearLayout) row.getChildAt(position[1]);
            return item;
        }
        return null;
    }

    public View getItemView(int day){
        RatioLinearLayout item = getItemViewParent(day);
        if (item != null){
            return item.getChildAt(0);
        }
        return null;
    }

    /**
     *
     * @param year
     * @param month  从0开始
     * @param day
     */
    public void setDate(int year, int month, int day){
        if (year == mCurYear && month == mCurMonth && day == mCurDay) return;
        mCurCalendar.set(Calendar.YEAR, year);
        mCurCalendar.set(Calendar.MONTH, month);
        mCurCalendar.set(Calendar.DATE, day);
        boolean refreshItem = false;
        if (year == mCurYear && month == mCurMonth){
            View curSelect = getItemView(mCurDay);
            if (curSelect != null){
                curSelect.setSelected(false);
                getItemView(day).setSelected(true);
            }else{
                refreshItem = true;
            }
        }else{
            refreshItem = true;
        }
        mCurYear = year;
        mCurMonth = month;
        mCurDay = day;
        if (refreshItem) refreshItem();
    }

    public void showTitle(boolean b){
        if (b != mShowTitle){
            mTvTitle.setVisibility(b?VISIBLE:GONE);
            mShowTitle = b;
        }
    }

    public void showWeek(boolean b){
        if (b != mShowWeek){
            mWeekContainer.setVisibility(b?VISIBLE:GONE);
            mShowWeek = b;
        }
    }

}
