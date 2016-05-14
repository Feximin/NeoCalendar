package com.feximin.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Neo on 16/5/14.
 */
public class RatioLinearLayout extends LinearLayout {
    private float mRatio = -1;
    public RatioLinearLayout(Context context) {
        this(context, null);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioLinearLayout);
            mRatio = ta.getFloat(0, -1);
            ta.recycle();
        }
    }

    public void setRatio(float ratio){
        if (mRatio != ratio){
            mRatio = ratio;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio > 0){
            int width = getDefaultSize3(getSuggestedMinimumWidth(), widthMeasureSpec);
            int height = (int) (width / mRatio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static int getDefaultSize3(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
}
