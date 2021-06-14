package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.PostVO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    @POST("post/add")
    Call<Map<String, Object>> addPost(@Header("x-access-token") String token, @Body PostVO form);

    @GET("post/list")
    Call<List<PostVO>> getPostList(@Query("date") String date);

    @Multipart
    @POST("post/upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part image, @Part("upload") RequestBody name);
}