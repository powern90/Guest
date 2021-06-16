package kw.kimkihong.assign3.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.os.Bundle;
import kw.kimkihong.assign3.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateActivity extends Activity {

    //decalre Activity variable
    private DatePicker datePicker;
    private Button submit;

    //declare time variable
    private int mYear =0;
    private int mMonth=0;
    private int mDay=0;

    private String startDate;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        //set activity variable
        this.datePicker = findViewById(R.id.dateDatePicker);
        this.submit = findViewById(R.id.dateSubmit);

        this.startDate = "";
        this.endDate = "";

        //generate calender variable
        Calendar calendar = new GregorianCalendar();
        this.mYear = calendar.get(Calendar.YEAR);
        this.mMonth = calendar.get(Calendar.MONTH);
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);

        //get intent for constraint
        Intent intent = getIntent();
        this.startDate = intent.getStringExtra("startDate");
        this.endDate = intent.getStringExtra("endDate");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        //set start date and end date
        if(this.startDate != null) {
            if (!this.startDate.equals("")) {
                try {
                    this.datePicker.setMinDate(format.parse(this.startDate).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if(this.endDate != null) {
            if (!this.endDate.equals("")) {
                try {
                    this.datePicker.setMaxDate(format.parse(this.endDate).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //connect onclick listener
        this.submit.setOnClickListener(new submitOnClickListener());
        this.datePicker.setOnDateChangedListener(new dateOnChangeListener());
    }

    //declare onclick listener
    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //go back to previous page
            Intent intent = new Intent();
            intent.putExtra("mYear",mYear);
            intent.putExtra("mMonth", mMonth);
            intent.putExtra("mDay", mDay);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //keep variable up to date
    class dateOnChangeListener implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDay = dd;
        }
    }
}