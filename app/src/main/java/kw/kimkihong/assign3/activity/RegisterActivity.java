package kw.kimkihong.assign3.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.RequestCallback;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Declare Activity variable
    private TextInputLayout idLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout passwordCheckLayout;
    private TextInputLayout nameLayout;
    private TextInputLayout phoneLayout;
    private TextInputLayout addressLayout_1;
    private TextInputLayout addressLayout_2;
    private TextInputLayout businessNumberLayout;

    private EditText idInput;
    private EditText passwordInput;
    private EditText passwordCheckInput;
    private EditText nameInput;
    private EditText phoneInput;
    private EditText addressInput_1;
    private EditText addressInput_2;
    private EditText businessNumberInput;
    private RadioGroup genderSelect;
    private RadioGroup businessSelect;
    private Button check;
    private Button submit;

    //declare Intent variable
    private Intent intent_main;

    //Declare check variable
    private boolean idCheck;
    private boolean pwCheck;
    private boolean business;
    private Integer gender;

    //declare shared preference
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //set activity vriable
        this.idLayout = findViewById(R.id.registerId);
        this.passwordLayout = findViewById(R.id.registerPassword);
        this.passwordCheckLayout = findViewById(R.id.registerPasswordCheck);
        this.nameLayout = findViewById(R.id.registerName);
        this.phoneLayout = findViewById(R.id.registerPhone);
        this.addressLayout_1 = findViewById(R.id.registerAddress_1);
        this.addressLayout_2 = findViewById(R.id.registerAddress_2);
        this.businessNumberLayout = findViewById(R.id.registerBusinessNumber);

        this.idInput = (EditText) findViewById(R.id.registerIdInput);
        this.passwordInput = (EditText) findViewById(R.id.registerPasswordInput);
        this.passwordCheckInput = (EditText) findViewById(R.id.registerPasswordCheckInput);
        this.nameInput = (EditText) findViewById(R.id.registerNameInput);
        this.phoneInput = (EditText) findViewById(R.id.registerPhoneInput);
        this.addressInput_1 = (EditText) findViewById(R.id.registerAddressInput_1);
        this.addressInput_2 = (EditText) findViewById(R.id.registerAddressInput_2);
        this.businessNumberInput = (EditText) findViewById(R.id.registerBusinessNumberInput);
        this.genderSelect = findViewById(R.id.registerGenderSelect);
        this.businessSelect = findViewById(R.id.registerBusinessSelect);
        this.check = (Button) findViewById(R.id.registerIdCheck);
        this.submit = (Button) findViewById(R.id.registerSubmit);

        //set intent variable
        this.intent_main = new Intent(this, MainActivity.class);

        //set onclick listener
        this.passwordCheckInput.setOnFocusChangeListener(new passwordOnFocusChangedListener());
        this.businessSelect.setOnCheckedChangeListener(new registerOnCheckListener());
        this.genderSelect.setOnCheckedChangeListener(new genderOnCheckListener());
        this.check.setOnClickListener(new checkOnClickListener());
        this.submit.setOnClickListener(new submitOnClickListener());

        //set check variable
        this.idCheck = false;
        this.pwCheck = false;
        this.business = false;
        this.gender = 0;

        this.sharedPreferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

//    class iddOnFocusChangedListener implements View.OnFocusChangeListener {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            String id = idInput.getText().toString().trim();
//            if(!hasFocus || id.equals("")) {
//                idLayout.setError("아이디를 입력하세요");
//                idCheck = false;
//            }
//        }
//    }

    //declare password check listener
    class passwordOnFocusChangedListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //if password check is different
            if(!hasFocus
                    && !passwordInput.getText().toString().equals(passwordCheckInput.getText().toString())
                    || passwordInput.getText().toString().trim().equals("")) {
                //mark warning
                passwordCheckLayout.setError("비밀번호가 다릅니다.");
                pwCheck = false;
            }
            else {
                passwordCheckLayout.setError(null);
                pwCheck = true;
            }
        }
    }

    //declare register onclick listener
    class registerOnCheckListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            //set value if radio button changed
            if(i == R.id.registerBusinessTrue) {
                businessNumberLayout.setVisibility(View.VISIBLE);
                business = true;
            }
            else {
                businessNumberInput.setText("");
                businessNumberLayout.setVisibility(View.GONE);
                business = false;
            }
        }
    }

    //declare gender onclick listener
    class genderOnCheckListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            //set value if radio button changed
            if(i == R.id.registerGenderMale) {
                gender = 0;
            }
            else {
                gender = 1;
            }
        }
    }

    //declare id check onclick listener
    class checkOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String id = idInput.getText().toString().trim();
            //if id is empty
            if(id.equals("")) {
                idLayout.setError("아이디를 입력하세요");
                idCheck = false;
            }
            else {
                //call API for check id
                Request.getInstance().checkID(id, new RequestCallback() {
                    @Override
                    public void onSuccess(Map<String, Object> retData) {
                        //if id is usable
                        Toast.makeText(getApplicationContext(), "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        idLayout.setError(null);
                        idCheck = true;
                    }

                    @Override
                    public void onError() {
                        idLayout.setError("중복된 아이디");
                        idCheck = false;
                    }
                });

            }
        }
    }

    //declare submit onclick listener
    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //check every input is valid
            if(!idCheck || !pwCheck) {
                Toast.makeText(getApplicationContext(), "아이디 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            String id = idInput.getText().toString().trim();
            String password = "";
            if(passwordInput.getText().toString().equals(passwordCheckInput.getText().toString())
                    && !passwordInput.getText().toString().trim().equals("")) {
                password = passwordInput.getText().toString().trim();
            }
            else {
                Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = nameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String address = (addressInput_1.getText().toString().trim() + " " + addressInput_2.getText().toString().trim()).trim();
            if(name.equals("") || phone.equals("") || address.equals("")) {
                Toast.makeText(getApplicationContext(), "모든 칸을 채워주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            String businessNumber = businessNumberInput.getText().toString().trim();
            if(business && businessNumber.equals("")) {
                Toast.makeText(getApplicationContext(), "사업자등록번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            //create form data
            Map<String, Object> form = new HashMap<>();
            form.put("id", id);
            form.put("password", password);
            form.put("name", name);
            form.put("phone", phone);
            form.put("address", address);
            form.put("gender", gender);
            form.put("isBusiness", business);
            form.put("business", businessNumber);
            String finalPassword = password;
            //call API for enroll
            Request.getInstance().enroll(form, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("id", id);
                    editor.putString("password", finalPassword);
                    editor.putBoolean("isBusiness", business);
                    editor.apply();
                    Request.getInstance().login(id, finalPassword, new RequestCallback() {
                        //if enroll is success
                        @Override
                        public void onSuccess(Map<String, Object> retData) {
                            //goto main activity
                            startActivity(intent_main);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                @Override
                public void onError() {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}