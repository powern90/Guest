package kw.kimkihong.assign3.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import kw.kimkihong.assign3.R;
import kw.kimkihong.retrofit.Request;
import kw.kimkihong.retrofit.RequestCallback;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText  idInput;
    private EditText  passwordInput;
    private Button    submit;
    private Button    register;

    private Intent    intent_main;
    private Intent    intent_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.idInput        = findViewById(R.id.loginIdInput);
        this.passwordInput  = findViewById(R.id.loginPasswordInput);
        this.submit         = findViewById(R.id.loginSubmit);
        this.register       = findViewById(R.id.loginRegister);

        this.intent_main = new Intent(this, MainActivity.class);
        this.intent_register = new Intent(this, RegisterActivity.class);

        this.submit.setOnClickListener(new submitOnClickListener());
        this.register.setOnClickListener(new registerOnClickListener());
    }

    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String id = idInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if(id.equals("")) {
                Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                return;
            }
            else if(password.equals("")) {
                Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                return;
            }
            Request.getInstance().login(id, password, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    startActivity(intent_main);
                }

                @Override
                public void onError() {
                    Toast.makeText(getApplicationContext(), "아이디랑 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    class registerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(intent_register);
        }
    }
}