package kw.kimkihong.assign3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.RequestCallback;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    //declare intent variable
    private Intent intent_login;
    private Intent intent_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //set intent variable
        this.intent_login = new Intent(this, LoginActivity.class);
        this.intent_main = new Intent(this, MainActivity.class);

        long start = System.currentTimeMillis();
        Request.getInstance().check(new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
                //check time and show spash screen for same time
                long pause = (System.currentTimeMillis() - start) >= 1000 ? 0 : 1000 - (System.currentTimeMillis() - start);
                new Handler(Looper.getMainLooper()).postDelayed(() -> startActivity(intent_main), pause);
            }

            @Override
            public void onError() {
                long pause = (System.currentTimeMillis() - start) >= 1000 ? 0 : 1000 - (System.currentTimeMillis() - start);
                new Handler(Looper.getMainLooper()).postDelayed(() -> startActivity(intent_login), pause);
            }
        });
    }
}
