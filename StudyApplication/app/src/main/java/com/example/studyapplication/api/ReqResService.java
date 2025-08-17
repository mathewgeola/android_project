package com.example.studyapplication.api;

import com.example.studyapplication.anno.Mock;
import com.example.studyapplication.data.CreateUserBean;
import com.example.studyapplication.data.LoginTokenBean;
import com.example.studyapplication.data.RegisterTokenBean;
import com.example.studyapplication.data.SingleUserBean;
import com.example.studyapplication.data.UserListBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReqResService {
    @GET("/api/users")
    @Headers({"x-api-key: reqres-free-v1"})
    Call<UserListBean> userList(@Query("page") Integer page);


    /*
    {
    "name": "morpheus",
    "job": "leader"
    }
    */
//    application/json; charset=utf-8
    @POST("/api/users")
    @FormUrlEncoded
    Call<CreateUserBean> createUser(@Field("name") String name, @Field("job") String job);

    /*
    {
    "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
        "avatar": "https://reqres.in/img/faces/2-image.jpg"
    },
    "support": {
        "url": "https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral",
        "text": "Tired of writing endless social media content? Let Content Caddy generate it for you."
    }
    }
    */
    @Mock(value = "{\n" + "    \"data\": {\n" + "        \"id\": 2,\n" + "        \"email\": \"janet.weaver@reqres.in\",\n" + "        \"first_name\": \"Janet\",\n" + "        \"last_name\": \"Weaver\",\n" + "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" + "    },\n" + "    \"support\": {\n" + "        \"url\": \"https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral\",\n" + "        \"text\": \"Tired of writing endless social media content? Let Content Caddy generate it for you.\"\n" + "    }\n" + "}")
    @GET("/api/users/{userId}")
    Call<SingleUserBean> getSingleUser(@Path("userId") Integer userId);

    @POST("api/register")
    @FormUrlEncoded
    @Headers({"x-api-key: reqres-free-v1"})
    Call<RegisterTokenBean> register(@Field("email") String email, @Field("password") String password);

    @POST("api/login")
    @FormUrlEncoded
    @Headers({"x-api-key: reqres-free-v1"})
    Call<LoginTokenBean> login(@Field("email") String email, @Field("password") String password);
}
