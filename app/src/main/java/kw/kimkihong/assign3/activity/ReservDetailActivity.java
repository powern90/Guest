package kw.kimkihong.assign3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import kw.kimkihong.assign3.R;

public class ReservDetailActivity extends AppCompatActivity {

    //Declare Activity variable
    private TextView count;
    private TextView startDate;
    private TextView endDate;
    private TextView payment;
    private TextView reservId;
    private TextView createdDate;
    private TextView userName;
    private TextView businessName;
    private LinearLayout userNameContainer;
    private LinearLayout businessNameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_detail);

        //set activity variable
        this.count = findViewById(R.id.rDetailCount);
        this.startDate = findViewById(R.id.rDetailStartDate);
        this.endDate = findViewById(R.id.rDetailEndDate);
        this.payment = findViewById(R.id.rDetailPayment);
        this.reservId = findViewById(R.id.rDetailId);
        this.createdDate = findViewById(R.id.rDetailCreatedAt);
        this.userName = findViewById(R.id.rDetailUserName);
        this.businessName = findViewById(R.id.rDetailBusinessName);
        this.userNameContainer = findViewById(R.id.rDetailUserNameContainer);
        this.businessNameContainer = findViewById(R.id.rDetailBusinessNameContainer);

        //get intent data
        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra("data");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        //check data is not null
        if(data != null) {
            //set each view
            this.count.setText(data[0]);
            this.startDate.setText(data[1]);
            this.endDate.setText(data[2]);
            this.payment.setText(data[3]);
            this.reservId.setText(data[4]);
            this.createdDate.setText(data[7]);
            if(preferences.getBoolean("isBusiness", false)) {
                this.businessNameContainer.setVisibility(View.GONE);
                this.userName.setText(data[8]);
            }
            else {
                this.userNameContainer.setVisibility(View.GONE);
                this.businessName.setText(data[9]);
            }
        }
    }
}