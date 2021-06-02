package kw.kimkihong.assign3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import kw.kimkihong.retrofit.Request;
import kw.kimkihong.retrofit.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent_login = new Intent(this, LoginActivity.class);
        Intent intent_main = new Intent(this, MainActivity.class);

        long start = System.currentTimeMillis();
        this.request = new Request(this);

        this.request.check(new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
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
