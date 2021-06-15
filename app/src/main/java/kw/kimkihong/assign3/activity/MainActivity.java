package kw.kimkihong.assign3.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kw.kimkihong.assign3.*;
import kw.kimkihong.assign3.fragment.*;
import kw.kimkihong.assign3.util.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {
    //Declare variable
    private BackPressCloseHandler backKeyClickHandler;
    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get shared preference
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Log.d("Token", token);


        //create back key press listener
        backKeyClickHandler = new BackPressCloseHandler(this);

        //set shared preference variable
        this.sharedPreferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        //set bottom navigation
        this.bottomNavigationView = findViewById(R.id.mainBottomNavigation);
        //check which kind of user is logged in
        if(this.sharedPreferences.getBoolean("isBusiness", true)) {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.business_bottom_navigation_menu);
            this.bottomNavigationView.setOnNavigationItemSelectedListener(new onNavigationItemSelectedListener());
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ReservationFragment()).commit();
            return;
        }

        //start bottom navigation
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new onNavigationItemSelectedListener());

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ListFragment()).commit();
    }

    //connect back key press listener
    @Override
    public void onBackPressed() {
        backKeyClickHandler.onBackPressed();
    }

    //declare navigation item selet listener
    class onNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //connect fragment for each item
            if(item.getItemId() == R.id.page_list) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ListFragment()).commit();
            }
            else if(item.getItemId() == R.id.page_resolve) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ReservationFragment()).commit();
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