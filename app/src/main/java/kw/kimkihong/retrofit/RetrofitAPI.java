package kw.kimkihong.retrofit;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RetrofitAPI {
    @FormUrlEncoded
    @POST("test")
    Call<Map<String, Object>> test(@Field("aaa") String aaa);

    @GET("auth/check")
    Call<Map<String, Object>> check(@Header("x-access-token") String token);

    @FormUrlEncoded
    @POST("auth/login")
    Call<Map<String, Object>> login(@Field("id") String id, @Field("password") String password);
}