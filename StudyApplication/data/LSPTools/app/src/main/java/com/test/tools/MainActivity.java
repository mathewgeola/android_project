package com.test.tools;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ActivityMain";

    private EditText packageNameEt;
    private EditText soNameEt;
    private Button saveBtn;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageNameEt = findViewById(R.id.package_name_et);
        soNameEt = findViewById(R.id.so_name_et);
        saveBtn = findViewById(R.id.save_btn);

        initView();
    }

    private void initView() {
        saveBtn.setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.test.tools.provider/app");

            ContentValues contentValues = new ContentValues();
            contentValues.put("package_name", packageNameEt.getText().toString());
            contentValues.put("so_name", soNameEt.getText().toString());

            ContentResolver contentResolver = getContentResolver();
            contentResolver.insert(uri, contentValues);

            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        });
    }
}