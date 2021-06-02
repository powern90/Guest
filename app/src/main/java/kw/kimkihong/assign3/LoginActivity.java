package kw.kimkihong.assign3;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import kw.kimkihong.retrofit.Request;
import kw.kimkihong.retrofit.RequestCallback;

import java.text.BreakIterator;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText  idInput;
    EditText  passwordInput;
    Button    loginSubmit;

    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.idInput        = findViewById(R.id.idInput);
        this.passwordInput  = findViewById(R.id.passwordInput);
        this.loginSubmit    = findViewById(R.id.loginSubmit);

        this.request = new Request(this);

        this.loginSubmit.setOnClickListener(new loginSubmitOnClickListener());
    }

    class loginSubmitOnClickListener implements View.OnClickListener {
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
            request.login(id, password, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {

                }

                @Override
                public void onError() {
                    Toast.makeText(getApplicationContext(), "로그인 실패 아이디랑 비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}