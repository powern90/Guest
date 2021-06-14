package kw.kimkihong.assign3.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.os.Bundle;
import kw.kimkihong.assign3.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateActivity extends Activity {

    private DatePicker datePicker;
    private Button submit;

    private int mYear =0;
    private int mMonth=0;
    private int mDay=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        this.datePicker = findViewById(R.id.dateDatePicker);
        this.submit = findViewById(R.id.dateSubmit);

        Calendar calendar = new GregorianCalendar();
        this.mYear = calendar.get(Calendar.YEAR);
        this.mMonth = calendar.get(Calendar.MONTH);
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);

        this.submit.setOnClickListener(new submitOnClickListener());
        this.datePicker.setOnDateChangedListener(new dateOnChangeListener());
    }

    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("mYear",mYear);
            intent.putExtra("mMonth", mMonth);
            intent.putExtra("mDay", mDay);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    class dateOnChangeListener implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDay = dd;
        }
    }
}