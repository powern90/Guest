package kw.kimkihong.retrofit;

import kw.kimkihong.vo.PostVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
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

    @FormUrlEncoded
    @POST("auth/id")
    Call<Map<String, Object>> checkID(@Field("id") String id);

    @POST("auth/enroll")
    Call<Map<String, Object>> enroll(@Body Map<String, Object> form);

    @GET("post/list")
    Call<List<PostVO>> getPostList(@Query("date") String date);
}