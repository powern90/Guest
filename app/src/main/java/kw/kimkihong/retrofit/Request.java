package kw.kimkihong.retrofit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

public class Request extends Application {
    private static Request singleton;
    private SharedPreferences preferences;
    private String token;


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        this.preferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        this.token = this.preferences.getString("token", "");
    }

    public static Request getInstance() {
        return singleton;
    }

    private void send_request(Call<Map<String, Object>> call, final RequestCallback callback) {
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                if(!response.isSuccessful()){
                    Log.e("API Connection Failed  ", "error code : " + response.code());
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body());
                Log.d("API Connection Success  ", response.body().toString());
            }
            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, Throwable t) {
                Log.e("API ERROR ", t.getMessage());
                callback.onError();
            }
        });
    }

    public void check(final RequestCallback callback) {
        if(!this.token.equals("")) {
            Call<Map<String, Object>> call = RetrofitClient.getApiService().check(this.token);
            this.send_request(call, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    callback.onSuccess(retData);
                }

                @Override
                public void onError() {
                    String id = preferences.getString("id", "");
                    String password = preferences.getString("password", "");
                    login(id, password, callback);
                }
            });
        }
        else {
            String id = this.preferences.getString("id", "");
            String password = this.preferences.getString("password", "");
            this.login(id, password, callback);
        }
    }

    public void login(String id, String password, final RequestCallback callback) {
        if (id.equals("") || password.equals("")) {
            callback.onError();
            return;
        }
        Call<Map<String, Object>> call = RetrofitClient.getApiService().login(id, password);
        this.send_request(call, new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= preferences.edit();
                editor.putString("token", (String) retData.get("token"));
                editor.putString("id", id);
                editor.putString("password", password);
                editor.apply();
                callback.onSuccess(retData);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void checkID(String id, final RequestCallback callback) {
        if (id.equals("")) {
            callback.onError();
            return;
        }
        Call<Map<String, Object>> call = RetrofitClient.getApiService().checkID(id);
        this.send_request(call, new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
                callback.onSuccess(retData);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void enroll(Map<String, Object> form, final RequestCallback callback) {
        Call<Map<String, Object>> call = RetrofitClient.getApiService().enroll(form);
        this.send_request(call, new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
                callback.onSuccess(retData);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void test(Map<String, Object> reqData, final RequestCallback callback) {
        Call<Map<String, Object>> call = RetrofitClient.getApiService().test("aaaaaa");
//        SharedPreferences.Editor editor= this.preferences.edit();
//        editor.putString("test", "retrofit");
//        editor.apply();
        this.send_request(call, callback);
    }


}
