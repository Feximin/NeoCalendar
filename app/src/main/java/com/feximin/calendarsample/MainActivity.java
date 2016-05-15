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

        calendar.setDate(2016, mMonth, mDay);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                calendar.nextMonth();
                calendar.autoFillTitle();
                calendar.postDelayed(this, 2000);
            }
        };
        calendar.postDelayed(runnable, 3000);
    }
}
