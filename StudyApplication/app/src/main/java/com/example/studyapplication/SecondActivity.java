package com.example.studyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            int age = intent.getIntExtra("age", -1);
            Log.d(TAG, "onCreate: username = " + username + ", age = " + age);
        }

        findViewById(R.id.finish_second_activity_button).setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("gender", "male");
            setResult(RESULT_OK, resultIntent);

            finish();
        });

    }
}