package kw.kimkihong.assign3.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.os.Bundle;
import kw.kimkihong.assign3.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeActivity extends Activity {

    private TimePicker timePicker;
    private Button submit;

    private int mHour = 0;
    private int mMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_time);

        this.timePicker = findViewById(R.id.timeTimePicker);
        this.submit = findViewById(R.id.timeSubmit);

        Calendar calendar = new GregorianCalendar();
        this.mHour = calendar.get(Calendar.HOUR);
        this.mMinute = calendar.get(Calendar.MINUTE);

        this.submit.setOnClickListener(new TimeActivity.submitOnClickListener());
        this.timePicker.setOnTimeChangedListener(new TimeActivity.timeOnChangeListener());
    }

    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("mHour",mHour);
            intent.putExtra("mMinute", mMinute);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    class timeOnChangeListener implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker timePicker, int hh, int mm) {
            mHour = hh;
            mMinute = mm;
        }
    }
}