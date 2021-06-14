package kw.kimkihong.retrofit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import kw.kimkihong.vo.PostVO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private void setPreferences(Map<String, Object> data) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= preferences.edit();
        editor.putString("name", (String) data.get("name"));
        editor.putBoolean("isBusiness", (boolean) data.get("isBusiness"));
        editor.apply();
    }

    public void check(final RequestCallback callback) {
        if(!this.token.equals("")) {
            Call<Map<String, Object>> call = RetrofitClient.getApiService().check(this.token);
            this.send_request(call, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    setPreferences((Map<String, Object>) retData.get("info"));
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
                token = (String) retData.get("token");
                editor.putString("id", id);
                editor.putString("password", password);
                editor.apply();
                check(new RequestCallback() {
                    @Override
                    public void onSuccess(Map<String, Object> retData) {
                        callback.onSuccess(retData);
                    }

                    @Override
                    public void onError() {
                        callback.onError();
                    }
                });
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

    public void getPostList(Calendar date, final PostCallback callback) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Call<List<PostVO>> call = RetrofitClient.getApiService().getPostList(df.format(date.getTime()));
        call.enqueue(new Callback<List<PostVO>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostVO>> call, @NotNull Response<List<PostVO>> response) {
                if(!response.isSuccessful()){
                    Log.e("API Connection Failed  ", "error code : " + response.code());
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body());
                assert response.body() != null;
                Log.d("API Connection Success  ", response.body().toString());
            }

            @Override
            public void onFailure(@NotNull Call<List<PostVO>> call, @NotNull Throwable t) {
                Log.e("API ERROR ", t.getMessage());
                callback.onError();
            }
        });
    }

    public void uploadImg(MultipartBody.Part partImage, RequestCallback callback) {
        Call<ResponseBody> call = RetrofitClient.getApiService().upload(partImage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Map<String, Object> ret = new HashMap<>();
                    ret.put("success", true);
                    callback.onSuccess(ret);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
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
