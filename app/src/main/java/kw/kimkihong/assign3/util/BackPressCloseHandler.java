package kw.kimkihong.assign3.util;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    //declare variable
    private long backKeyClickTime = 0;
    private final Activity activity;

    public BackPressCloseHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        //check first press
        if (System.currentTimeMillis() > backKeyClickTime + 2000) {
            backKeyClickTime = System.currentTimeMillis();
            showToast();
            return;
        }
        //check second press and calculate time interval
        if (System.currentTimeMillis() <= backKeyClickTime + 2000) {
            activity.finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    public void showToast() {
        Toast.makeText(activity, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
