package com.example.studyapplication;

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
import com.example.studyapplication.databinding.ActivityNetworkDemoBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkDemoActivity extends AppCompatActivity {

    private static final String TAG = "NetworkDemoActivity";

    private ActivityNetworkDemoBinding binding;

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
    }

    private void reInitView() {
        binding.responseStatusTv.setText("");
        binding.responseContentTv.setText("");
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
}