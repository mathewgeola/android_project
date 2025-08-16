package com.example.studyapplication.interceptors;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.studyapplication.anno.Mock;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Invocation;

public class MockInterceptor implements Interceptor {
    private Method getRetrofitMethod(Request request) {
        Invocation tag = request.tag(Invocation.class);
        if (tag != null) {
            return tag.method();
        }
        return null;
    }

    private Mock getMock(Method method) {
        return method.getAnnotation(Mock.class);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Method method = getRetrofitMethod(request);
        if (method != null) {
            Mock mock = getMock(method);
            if (mock != null && !TextUtils.isEmpty(mock.value())) {
                return new Response.Builder().protocol(Protocol.HTTP_1_0).code(200).request(request).message("ok").body(ResponseBody.create(mock.value(), MediaType.parse("application/json; charset=utf-8"))).build();
            }
        }
        return chain.proceed(request);
    }
}
