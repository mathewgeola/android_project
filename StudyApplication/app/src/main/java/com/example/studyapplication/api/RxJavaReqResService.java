package com.example.studyapplication.api;

import com.example.studyapplication.data.LoginTokenBean;
import com.example.studyapplication.data.RegisterTokenBean;
import com.example.studyapplication.data.SingleUserBean;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RxJavaReqResService {
    @POST("api/register")
    @FormUrlEncoded
    @Headers({"x-api-key: reqres-free-v1"})
    Observable<RegisterTokenBean> register(@Field("email") String email, @Field("password") String password);

    @POST("api/login")
    @FormUrlEncoded
    @Headers({"x-api-key: reqres-free-v1"})
    Observable<LoginTokenBean> login(@Field("email") String email, @Field("password") String password);

    @GET("/api/users/{user_id}")
    @Headers({"x-api-key: reqres-free-v1"})
    Observable<SingleUserBean> getSingleUser(@Path("user_id") Integer userId);

    /* https://reqres.in/img/faces/2-image.jpg */
    @GET("/img/faces/{path}")
    Observable<ResponseBody> getAvatar(@Path("path") String path);
}
