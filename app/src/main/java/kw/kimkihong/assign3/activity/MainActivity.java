package kw.kimkihong.assign3.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kw.kimkihong.assign3.*;
import kw.kimkihong.assign3.fragment.*;
import kw.kimkihong.assign3.util.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backKeyClickHandler;
    private SharedPreferences sharedPreferences;

    Button temp;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Log.d("Token", token);


        backKeyClickHandler = new BackPressCloseHandler(this);

        this.sharedPreferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

//        this.temp = findViewById(R.id.tmp);
        this.bottomNavigationView = findViewById(R.id.mainBottomNavigation);
        if(this.sharedPreferences.getBoolean("isBusiness", true)) {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.business_bottom_navigation_menu);
            this.bottomNavigationView.setOnNavigationItemSelectedListener(new onNavigationItemSelectedListener());
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ReservationFragment()).commit();
            return;
        }

//        this.temp.setOnClickListener(new tmpOnClickListener());
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new onNavigationItemSelectedListener());

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ListFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        backKeyClickHandler.onBackPressed();
    }


    class onNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.page_list) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ListFragment()).commit();
            }
            else if(item.getItemId() == R.id.page_resolve) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ResolveFragment()).commit();
            }
            else if(item.getItemId() == R.id.page_reservation) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ReservationFragment()).commit();
            }
            else if(item.getItemId() == R.id.page_add) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AddFragment()).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new MyFragment()).commit();
            }
            return true;
        }
    }

}