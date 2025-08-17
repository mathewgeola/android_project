package com.example.studyapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyapplication.api.ReqResService;
import com.example.studyapplication.api.RxJavaReqResService;
import com.example.studyapplication.data.CreateUserBean;
import com.example.studyapplication.data.LoginTokenBean;
import com.example.studyapplication.data.RegisterTokenBean;
import com.example.studyapplication.data.SingleUserBean;
import com.example.studyapplication.data.UserListBean;
import com.example.studyapplication.databinding.ActivityNetworkDemoBinding;
import com.example.studyapplication.interceptors.CustomNetworkInterceptor;
import com.example.studyapplication.interceptors.MockInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkDemoActivity extends AppCompatActivity {

    private static final String TAG = "NetworkDemoActivity";

    private ActivityNetworkDemoBinding binding;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_network_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityNetworkDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.httpUrlConnectionGetBtn.setOnClickListener(v -> {
            reInitView();
            new Thread(this::httpUrlConnectionGet).start();
        });

        binding.httpUrlConnectionPostBtn.setOnClickListener(v -> {
            reInitView();
            new Thread(this::httpUrlConnectionPost).start();
        });

        binding.volleyGetBtn.setOnClickListener(v -> {
            reInitView();
            volleyGet();
        });

        binding.volleyGetJsonBtn.setOnClickListener(v -> {
            reInitView();
            volleyGetJson();
        });

        binding.okhttpGetSyncBtn.setOnClickListener(v -> {
            reInitView();
            okhttpGetSync();
        });

        binding.okhttpGetAsyncBtn.setOnClickListener(v -> {
            reInitView();
            okhttpGetAsync();
        });

        binding.okhttpPostAsyncBtn.setOnClickListener(v -> {
            reInitView();
            okhttpPostAsync();
        });

        binding.okhttpGetAsyncInterceptorsBtn.setOnClickListener(v -> {
            reInitView();
            okhttpGetAsyncInterceptors();
        });

        binding.retrofitGetBtn.setOnClickListener(v -> {
            reInitView();
            retrofitGet();
        });

        binding.retrofitPostBtn.setOnClickListener(v -> {
            reInitView();
            retrofitPost();
        });

        binding.retrofitPostMockBtn.setOnClickListener(v -> {
            reInitView();
            retrofitPostMock();
        });

        binding.retrofitPostComplexBtn.setOnClickListener(v -> {
            reInitView();
            retrofitPostComplex();
        });

        binding.retrofitPostComplexRxjavaBtn.setOnClickListener(v -> {
            reInitView();
            retrofitPostComplexRxJava();
        });

        binding.retrofitPostComplexRxjavaExampleBtn.setOnClickListener(v -> {
            reInitView();
            retrofitPostComplexRxJavaExample();
        });
    }

    private void reInitView() {
        binding.responseStatusTv.setText("");
        binding.responseContentTv.setText("");
        binding.nameTv.setText("");
        binding.signTv.setText("");
        binding.avatarIv.setImageResource(R.mipmap.ic_launcher);
    }

    private void httpUrlConnectionGet() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://reqres.in/api/users?page=2");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
                is.close();
            }

            Log.d(TAG, "httpUrlConnectionGet: sb = " + sb);

            int responseCode = connection.getResponseCode();

            runOnUiThread(() -> {
                binding.responseStatusTv.setText(String.valueOf(responseCode));
                binding.responseContentTv.setText(sb.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void httpUrlConnectionPost() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://reqres.in/api/users");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("x-api-key", "reqres-free-v1");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            JSONObject data = new JSONObject();
            /*
            {
                "name": "morpheus",
                "job": "leader"
            }
            */
            try {
                data.put("name", "morpheus");
                data.put("job", "leader");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] bytes = data.toString().getBytes();
            connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
                is.close();
            }

            Log.d(TAG, "httpUrlConnectionGet: sb = " + sb);

            int responseCode = connection.getResponseCode();

            runOnUiThread(() -> {
                binding.responseStatusTv.setText(String.valueOf(responseCode));
                binding.responseContentTv.setText(sb.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void volleyGet() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, "https://reqres.in/api/users?page=2", s -> {
            Log.d(TAG, "onResponse:  s = " + s);

            runOnUiThread(() -> {
                binding.responseContentTv.setText(s);
            });
        }, volleyError -> {
        }) {
            @Override
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(statusCode));
                });
                String parsed = new String(response.data);
                return com.android.volley.Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(request);
    }

    private void volleyGetJson() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://reqres.in/api/users?page=2", null, jsonObject -> {
            Log.d(TAG, "onResponse:  jsonObject.toString() = " + jsonObject.toString());

            runOnUiThread(() -> {
                binding.responseContentTv.setText(jsonObject.toString());
            });
        }, volleyError -> {

        }) {
            @Override
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(statusCode));
                });
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    private void okhttpGetSync() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url("https://reqres.in/api/users?page=2").build();
        new Thread(() -> {
            try {
                okhttp3.Response response = client.newCall(request).execute();
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(response.code()));
                    try {
                        binding.responseContentTv.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void okhttpGetAsync() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url("https://reqres.in/api/users?page=2").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(response.code()));
                    try {
                        binding.responseContentTv.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void okhttpPostAsync() {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json; charset=UTF-8");
        JSONObject data = new JSONObject();
        /*
        {
            "name": "morpheus",
            "job": "leader"
        }
        */
        try {
            data.put("name", "morpheus");
            data.put("job", "leader");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(data.toString(), mediaType);
        okhttp3.Request request = new okhttp3.Request.Builder().url("https://reqres.in/api/users").post(body).header("x-api-key", "reqres-free-v1").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(response.code()));
                    try {
                        binding.responseContentTv.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void okhttpGetAsyncInterceptors() {
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new CustomNetworkInterceptor()).build();
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new CustomNetworkInterceptor()).build();
        okhttp3.Request request = new okhttp3.Request.Builder().url("http://www.publicobject.com/helloworld.txt").header("User-Agent", "OkHttp Example").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> {
                    binding.responseStatusTv.setText(String.valueOf(response.code()));
                    try {
                        binding.responseContentTv.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void retrofitGet() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create()).build();
        ReqResService service = retrofit.create(ReqResService.class);
        service.userList(2).enqueue(new retrofit2.Callback<UserListBean>() {
            @Override
            public void onResponse(retrofit2.Call<UserListBean> call, retrofit2.Response<UserListBean> response) {
                if (response.isSuccessful()) {
                    UserListBean userListBean = response.body();
                    if (userListBean != null) {
                        binding.responseStatusTv.setText(String.valueOf(response.code()));
                        binding.responseContentTv.setText("总用户数为：" + userListBean.getTotal());
                    }

//                    OkHttpClient client = new OkHttpClient();
//                    okhttp3.Request request = new okhttp3.Request.Builder().url("https://reqres.in/api/users?page=2").build();
//                    try {
//                        client.newCall(request).execute();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    binding.responseStatusTv.setText(String.valueOf(response.code()));
                    binding.responseContentTv.setText("response.toString() = " + response);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserListBean> call, Throwable throwable) {

            }
        });
    }

    private void retrofitPost() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request original = chain.request();
            okhttp3.Request request = original.newBuilder().header("x-api-key", "reqres-free-v1").build();
            return chain.proceed(request);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in").client(client).addConverterFactory(GsonConverterFactory.create()).build();
        ReqResService service = retrofit.create(ReqResService.class);
        service.createUser("morpheus", "leader").enqueue(new retrofit2.Callback<CreateUserBean>() {
            @Override
            public void onResponse(retrofit2.Call<CreateUserBean> call, retrofit2.Response<CreateUserBean> response) {
                CreateUserBean createUserBean = response.body();
                binding.responseStatusTv.setText(String.valueOf(response.code()));
                if (createUserBean != null) {
                    binding.responseContentTv.setText(createUserBean.getName() + "：" + createUserBean.getJob());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CreateUserBean> call, Throwable throwable) {

            }
        });
    }

    private void retrofitPostMock() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new MockInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create()).build();
        ReqResService service = retrofit.create(ReqResService.class);
        service.getSingleUser(2).enqueue(new retrofit2.Callback<SingleUserBean>() {
            @Override
            public void onResponse(retrofit2.Call<SingleUserBean> call, retrofit2.Response<SingleUserBean> response) {
                SingleUserBean singleUserBean = response.body();
                binding.responseStatusTv.setText(String.valueOf(response.code()));
                if (singleUserBean != null) {
                    binding.responseContentTv.setText(singleUserBean.getData().getFirstName());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SingleUserBean> call, Throwable throwable) {

            }
        });
    }

    private void retrofitPostComplex() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create()).build();
        ReqResService service = retrofit.create(ReqResService.class);
        binding.responseStatusTv.setText("注册中……");
        service.register("eve.holt@reqres.in", "pistol").enqueue(new retrofit2.Callback<RegisterTokenBean>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterTokenBean> call, retrofit2.Response<RegisterTokenBean> response) {
                RegisterTokenBean registerTokenBean = response.body();
                binding.responseStatusTv.setText("注册完成");
                if (registerTokenBean != null) {
                    binding.responseContentTv.setText("获取到注册 token = " + registerTokenBean.getToken());
                }

                binding.responseStatusTv.setText("登录中……");
                service.login("eve.holt@reqres.in", "cityslicka").enqueue(new retrofit2.Callback<LoginTokenBean>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginTokenBean> call, retrofit2.Response<LoginTokenBean> response) {
                        LoginTokenBean loginTokenBean = response.body();
                        binding.responseStatusTv.setText("登录完成");
                        if (loginTokenBean != null) {
                            binding.responseContentTv.setText("获取到登录 token = " + loginTokenBean.getToken());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<LoginTokenBean> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<RegisterTokenBean> call, Throwable throwable) {

            }
        });
    }

    private void retrofitPostComplexRxJava() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        RxJavaReqResService service = retrofit.create(RxJavaReqResService.class);
        service.register("eve.holt@reqres.in", "pistol").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<RegisterTokenBean>() {
            @Override
            public void accept(RegisterTokenBean registerTokenBean) throws Throwable {
                binding.responseStatusTv.setText("注册完成");
                if (registerTokenBean != null) {
                    binding.responseContentTv.setText("获取到注册 token = " + registerTokenBean.getToken());
                }

                binding.responseStatusTv.setText("等待中……");
            }
        }).observeOn(Schedulers.io()).concatMap(new Function<RegisterTokenBean, ObservableSource<RegisterTokenBean>>() {
            @Override
            public ObservableSource<RegisterTokenBean> apply(RegisterTokenBean registerTokenBean) throws Throwable {
                return Observable.just(registerTokenBean).delay(3, TimeUnit.SECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<RegisterTokenBean>() {
            @Override
            public void accept(RegisterTokenBean registerTokenBean) throws Throwable {
                binding.responseStatusTv.setText("等待结束");
                binding.responseStatusTv.setText("登录中……");
            }
        }).observeOn(Schedulers.io()).concatMap(new Function<RegisterTokenBean, ObservableSource<LoginTokenBean>>() {
            @Override
            public ObservableSource<LoginTokenBean> apply(RegisterTokenBean registerTokenBean) throws Throwable {
                return service.login("eve.holt@reqres.in", "cityslicka");
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LoginTokenBean>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                mDisposable = d;
                binding.responseStatusTv.setText("注册中……");
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull LoginTokenBean loginTokenBean) {
                if (loginTokenBean != null) {
                    binding.responseContentTv.setText("获取到登录 token = " + loginTokenBean.getToken());
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                binding.responseStatusTv.setText("登录完成");
            }
        });
    }

    private void retrofitPostComplexRxJavaExample() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();

        RxJavaReqResService service = retrofit.create(RxJavaReqResService.class);

        service.register("eve.holt@reqres.in", "pistol").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<RegisterTokenBean>() {
            @Override
            public void accept(RegisterTokenBean registerTokenBean) throws Throwable {
                if (registerTokenBean != null) {
                    binding.responseContentTv.setText("获取到注册 token = " + registerTokenBean.getToken() + "，注册 id = " + registerTokenBean.getId());
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<RegisterTokenBean, Integer>() {
            @Override
            public Integer apply(RegisterTokenBean registerTokenBean) throws Throwable {
                return registerTokenBean.getId();
            }
        }).observeOn(Schedulers.io()).concatMap(new Function<Integer, ObservableSource<SingleUserBean>>() {
            @Override
            public ObservableSource<SingleUserBean> apply(Integer userId) throws Throwable {
                return service.getSingleUser(userId);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<SingleUserBean>() {
            @Override
            public void accept(SingleUserBean singleUserBean) throws Throwable {
                binding.nameTv.setText(singleUserBean.getData().getFirstName() + "-" + singleUserBean.getData().getLastName());
                binding.signTv.setText(singleUserBean.getSupport().getText());
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<SingleUserBean, String>() {
            @Override
            public String apply(SingleUserBean singleUserBean) throws Throwable {
                String avatar = singleUserBean.getData().getAvatar();
//                "https://reqres.in/img/faces/2-image.jpg"
                String[] paths = avatar.split("/");
                return paths[paths.length - 1];
            }
        }).observeOn(Schedulers.io()).concatMap(new Function<String, ObservableSource<ResponseBody>>() {
            @Override
            public ObservableSource<ResponseBody> apply(String path) throws Throwable {
                return service.getAvatar(path);
            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<ResponseBody, Bitmap>() {
            @Override
            public Bitmap apply(ResponseBody responseBody) throws Throwable {
                return BitmapFactory.decodeStream(responseBody.byteStream());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                mDisposable = d;
                binding.responseStatusTv.setText("注册中……");
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Bitmap bitmap) {
                binding.avatarIv.setImageBitmap(bitmap);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                binding.responseStatusTv.setText("获取完成");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}