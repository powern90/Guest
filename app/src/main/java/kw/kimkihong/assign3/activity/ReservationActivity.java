package kw.kimkihong.assign3.activity;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.RequestCallback;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {
    //Declare Activity variable
    private TextView startDate;
    private TextView endDate;
    private TextView price;
    private TextView name;
    private RadioGroup payment;
    private TextView count;
    private Button increase;
    private Button decrease;
    private Button submit;

    //declare value variable
    private int originPrice;
    private int mPrice;
    private int mDays;
    private int postId;
    private int mCount;
    private String paymentSelect;

    private LocalDate mStart;
    private LocalDate mEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        //set activity variable
        this.mCount = 1;

        this.startDate = findViewById(R.id.resStartDate);
        this.endDate = findViewById(R.id.resEndDate);
        this.price = findViewById(R.id.resPrice);
        this.payment = findViewById(R.id.resPayment);
        this.count = findViewById(R.id.resCount);
        this.name = findViewById(R.id.resName);
        this.increase = findViewById(R.id.resIncrease);
        this.decrease = findViewById(R.id.resDecrease);
        this.submit = findViewById(R.id.resSubmit);

        //set onclick listener
        this.payment.setOnCheckedChangeListener(new paymentOnCheckListener());
        this.increase.setOnClickListener(new increaseOnClickListener());
        this.decrease.setOnClickListener(new decreaseOnClickListener());
        this.submit.setOnClickListener(new submitOnClickListener());

        //set each value to show
        Intent intent = getIntent();
        this.postId = intent.getIntExtra("postId", 0);
        this.startDate.setText(intent.getStringExtra("startDate"));
        this.endDate.setText(intent.getStringExtra("endDate"));
        this.originPrice = intent.getIntExtra("price", 0);
        this.name.setText(intent.getStringExtra("postName"));

        this.paymentSelect = "신용카드";

        //calculate and format value
        this.mStart = LocalDate.parse(intent.getStringExtra("startDate"), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.mEnd = LocalDate.parse(intent.getStringExtra("endDate"), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.mDays = Integer.parseInt(String.valueOf(Duration.between(this.mStart.atStartOfDay(), this.mEnd.atStartOfDay()).toDays()));
        this.mPrice = this.mCount * this.originPrice * this.mDays;
        this.price.setText(String.valueOf(this.mPrice));
    }

    //declare increase onclick listener
    class increaseOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            //calculate value and show
            mCount = mCount + 1;
            count.setText(String.valueOf(mCount));
            mPrice = mCount*originPrice*mDays;
            price.setText(String.valueOf(mPrice));
        }
    }

    //declare decrease onclick listener
    class decreaseOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            //block to get negative value
            if(mCount > 1) {
                //calculate value and show
                mCount = mCount - 1;
                count.setText(String.valueOf(mCount));
                mPrice = (mCount*originPrice*mDays);
                price.setText(String.valueOf(mPrice));
            }
        }
    }

    //declare payment onclick listener
    class paymentOnCheckListener implements RadioGroup.OnCheckedChangeListener {
        //monitor checkbox is changed
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.resButton_1) {
                paymentSelect = "신용카드";
            }
            else {
                paymentSelect = "휴대폰";
            }
        }
    }

    //decalre submit onclick listener
    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //generate form data
            Map<String, Object> reqData = new HashMap<>();
            reqData.put("count", mCount);
            reqData.put("startDate", mStart);
            reqData.put("endDate", mEnd);
            reqData.put("payment", paymentSelect);
            reqData.put("postId", postId);
            //call API for Insert
            Request.getInstance().reservation(reqData, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    //go back to main activity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "결제에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

                @Override
                public void onError() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "결제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
        }
    }


}