package kw.kimkihong.assign3.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2/";

    public static RetrofitAPI getApiService(){
        return getInstance().create(RetrofitAPI.class);
    }

    //initialize instance
    private static Retrofit getInstance(){
        ObjectMapper mapper = new ObjectMapper();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
    }
}