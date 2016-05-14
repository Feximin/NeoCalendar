package com.feximin.calendarsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feximin.calendar.CalendarView;

public class MainActivity extends AppCompatActivity {

    private  int mDay = 8;
    private int mMonth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDay++;
                if (mDay > 31) mDay = 1;
                if (mMonth > 11) mMonth = 0;
                calendar.setDate(2016, mMonth, mDay);
                calendar.setTitleText(mMonth + 1 + "月" + mDay + "日");
                calendar.postDelayed(this, 2000);
            }
        };
        calendar.postDelayed(runnable, 3000);
    }
}
