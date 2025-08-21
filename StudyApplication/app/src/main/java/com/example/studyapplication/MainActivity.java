package com.example.studyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private void testCtrlAltM() {
//        Ctrl + Alt + M

//        int result = 0;
//        for (int i = 0; i < 10; i++) {
//            result += i;
//        }

        sumToTen();
    }

    private static void sumToTen() {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            result += i;
        }
    }

    private void testCtrlAltP() {
//        Ctrl + Alt + P

//        extracted();
//
//        private static void extracted() {
//            int result = 0;
//            for (int i = 0; i < 10; i++) {
//                result += i;
//            }
//        }

        extracted(0);
        extracted(1);
        extracted(2);
    }

    private static void extracted(int initValue) {
        for (int i = 0; i < 10; i++) {
            initValue += i;
        }
    }

    private void testCtrlAltV() {
//        Ctrl + Alt + V

//        new ArrayList<>();

        ArrayList<Object> objects = new ArrayList<>();
    }

    private void testCtrlAltT() {
//        Ctrl + Alt + T

//        int result = 0;
//        for (int i = 0; i < 10; i++) {
//            result += i;
//        }

        do {
            int result = 0;
            for (int i = 0; i < 10; i++) {
                result += i;
            }
        } while (true);
    }

    public static String threadDetect() {
        String threadName = "default";
        try {
            threadName = Looper.myLooper().getThread().getName();
        } catch (Exception ignored) {

        }
        return threadName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate: ");

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toV3Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        });

        binding.toV41Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UiDemoActivity.class);
            startActivity(intent);
        });

        binding.toV42Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListViewDemoActivity.class);
            startActivity(intent);
        });

        binding.toV43Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecyclerViewDemoActivity.class);
            startActivity(intent);
        });

        binding.toV5Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FragmentDemoActivity.class);
            startActivity(intent);
        });

        binding.toV6Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NetworkDemoActivity.class);
            startActivity(intent);
        });

        binding.toV10Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BroadcastDemoActivity.class);
            startActivity(intent);
        });

        Log.d(TAG, "主线程 Looper: " + Looper.getMainLooper());
        Log.d(TAG, "当前线程 Looper: " + Looper.myLooper());

        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            handler.post(() -> {
                Log.d(TAG, "handler 主线程 Looper: " + Looper.getMainLooper());
                Log.d(TAG, "handler 当前线程 Looper: " + Looper.myLooper());
            });
        }).start();


        new Thread(() -> {
            Log.d(TAG, "onCreate Thread: threadDetect = " + threadDetect());

            runOnUiThread(() -> {
                Log.d(TAG, "onCreate runOnUiThread: threadDetect = " + threadDetect());
            });

            handler.post(() -> {
                Log.d(TAG, "onCreate handler.post: threadDetect = " + threadDetect());
            });
        }).start();

        binding.toV13Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ServiceDemoActivity.class);
            startActivity(intent);
        });

        binding.toV14Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContentProviderDemoActivity.class);
            startActivity(intent);
        });
    }
}