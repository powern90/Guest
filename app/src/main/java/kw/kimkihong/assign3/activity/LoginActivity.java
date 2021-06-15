package kw.kimkihong.assign3.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.RequestCallback;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //Declare Activity variable
    private EditText  idInput;
    private EditText  passwordInput;
    private Button    submit;
    private Button    register;

    //Declare Intent variable
    private Intent    intent_main;
    private Intent    intent_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set activity variable
        this.idInput        = findViewById(R.id.loginIdInput);
        this.passwordInput  = findViewById(R.id.loginPasswordInput);
        this.submit         = findViewById(R.id.loginSubmit);
        this.register       = findViewById(R.id.loginRegister);

        //create intent variable
        this.intent_main = new Intent(this, MainActivity.class);
        this.intent_register = new Intent(this, RegisterActivity.class);

        //set onclick listener
        this.submit.setOnClickListener(new submitOnClickListener());
        this.register.setOnClickListener(new registerOnClickListener());
    }

    //declare submit onclick listener
    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //get user input
            String id = idInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            //check user input is validate
            if(id.equals("")) {
                Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                return;
            }
            else if(password.equals("")) {
                Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                return;
            }
            //call API for login
            Request.getInstance().login(id, password, new RequestCallback() {
                //if login success goto main activity
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    startActivity(intent_main);
                }

                //if login failed show toast message
                @Override
                public void onError() {
                    Toast.makeText(getApplicationContext(), "아이디랑 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //declare register onclick listener for register
    class registerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(intent_register);
        }
    }
}