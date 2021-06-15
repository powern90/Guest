package kw.kimkihong.assign3.retrofit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import kw.kimkihong.assign3.vo.PostVO;
import kw.kimkihong.assign3.vo.ReservationVO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.*;

public class Request extends Application {
    //declare static variable
    private static Request singleton;
    private SharedPreferences preferences;
    private String token;


    @Override
    public void onCreate() {
        super.onCreate();
        //set variable
        singleton = this;
        this.preferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        this.token = this.preferences.getString("token", "");
    }

    //declare getter
    public static Request getInstance() {
        return singleton;
    }

    //declare send request for multiple purpose
    private void send_request(Call<Map<String, Object>> call, final RequestCallback callback) {
        //put task in queue
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                //if response got error
                if(!response.isSuccessful()){
                    Log.e("API Connection Failed  ", "error code : " + response.code());
                    callback.onError();
                    return;
                }
                //return data
                callback.onSuccess(response.body());
                Log.d("API Connection Success  ", response.body().toString());
            }
            //on connection failure
            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, Throwable t) {
                Log.e("API ERROR ", t.getMessage());
                callback.onError();
            }
        });
    }

    //set shared preference value
    private void setPreferences(Map<String, Object> data) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= preferences.edit();
        editor.putString("name", (String) data.get("name"));
        editor.putBoolean("isBusiness", (boolean) data.get("isBusiness"));
        editor.apply();
    }

    //check token
    public void check(final RequestCallback callback) {
        //if there is token saved
        if(!this.token.equals("")) {
            //call for vaeify
            Call<Map<String, Object>> call = RetrofitClient.getApiService().check(this.token);
            this.send_request(call, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    //set return data as preference
                    setPreferences((Map<String, Object>) retData.get("info"));
                    callback.onSuccess(retData);
                }

                @Override
                public void onError() {
                    //get id and password to auto login
                    String id = preferences.getString("id", "");
                    String password = preferences.getString("password", "");
                    login(id, password, callback);
                }
            });
        }
        else {
            //get id password from preference
            String id = this.preferences.getString("id", "");
            String password = this.preferences.getString("password", "");
            this.login(id, password, callback);
        }
    }

    //login function
    public void login(String id, String password, final RequestCallback callback) {
        //check id and password exist
        if (id.equals("") || password.equals("")) {
            callback.onError();
            return;
        }
        //call API for login
        Call<Map<String, Object>> call = RetrofitClient.getApiService().login(id, password);
        this.send_request(call, new RequestCallback() {
            @Override
            public void onSuccess(Map<String, Object> retData) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= preferences.edit();
                //set preference
                editor.putString("token", (String) retData.get("token"));
                token = (String) retData.get("token");
                editor.putString("id", id);
                editor.putString("password", password);
                editor.apply();
                //verify token
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

    //check ID for enroll
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

    //enroll API
    public void enroll(Map<String, Object> form, final RequestCallback callback) {
        //connect API for enroll
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

    //get post list for normal user
    public void getPostList(Calendar date, final ListCallback callback) {
        //format date for API
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        //call API for list
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

    //upload image API
    public void uploadImg(MultipartBody.Part partImage, RequestBody name, RequestCallback callback) {
        Call<ResponseBody> call = RetrofitClient.getApiService().upload(partImage, name);
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

    //add post for business user
    public void addPost(PostVO form, final RequestCallback callback) {
        Call<Map<String, Object>> call = RetrofitClient.getApiService().addPost(this.token, form);
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

    //get post detail
    public void getPost(int id, final PostCallback callback) {
        Call<PostVO> call = RetrofitClient.getApiService().getPost(id);
        call.enqueue(new Callback<PostVO>() {
            @Override
            public void onResponse(@NotNull Call<PostVO> call, @NotNull Response<PostVO> response) {
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
            public void onFailure(@NotNull Call<PostVO> call, @NotNull Throwable t) {
                Log.e("API ERROR ", t.getMessage());
                callback.onError();
            }
        });
    }

    //make reservation API
    public void reservation(Map<String, Object> form, final RequestCallback callback) {
        Call<Map<String, Object>> call = RetrofitClient.getApiService().reservation(this.token, form);
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

    //get my reservation or my business client list
    public void getReservList(String id, Calendar date, final rListCallback callback) {
        Map<String, Object> form = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        form.put("id", id);
        form.put("date", df.format(date.getTime()));
        Call<List<ReservationVO>> call = RetrofitClient.getApiService().getReservList(this.token, form);
        call.enqueue(new Callback<List<ReservationVO>>() {
            @Override
            public void onResponse(@NotNull Call<List<ReservationVO>> call, @NotNull Response<List<ReservationVO>> response) {
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
            public void onFailure(@NotNull Call<List<ReservationVO>> call, @NotNull Throwable t) {
                Log.e("API ERROR ", t.getMessage());
                callback.onError();
            }
        });
    }
}
