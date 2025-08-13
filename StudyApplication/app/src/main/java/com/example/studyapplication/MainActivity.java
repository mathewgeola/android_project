package com.example.studyapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }
}