package com.example.studyapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.databinding.ActivityUiDemoBinding;

public class UiDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ui_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityUiDemoBinding binding = ActivityUiDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textview.setText("java text");

        class ButtonOnClickListenerClass implements View.OnClickListener {

            @Override
            public void onClick(View v) {

            }
        }

        binding.button.setOnClickListener(new ButtonOnClickListenerClass());

        binding.button.setOnClickListener(v -> {
            String string = binding.edittext.getText().toString();
            Toast.makeText(UiDemoActivity.this, "点击了按钮，输入内容为：" + string, Toast.LENGTH_SHORT).show();
        });
    }
}