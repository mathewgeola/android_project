package com.example.studyapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.contentprovider.MyContentProvider;
import com.example.studyapplication.utils.DBHelper;

public class ContentProviderDemoActivity extends AppCompatActivity {
    private static final String TAG = "ContentProviderDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content_provider_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Uri uri = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + DBHelper.TB_NAME);

        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", 3);
        contentValues.put("package_name", "com.example.studyapplication");
        contentValues.put("so_name", "libnative.so");

        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(uri, contentValues);

        Cursor cursor = contentResolver.query(uri, new String[]{"id", "package_name", "so_name"}, null, null, null);
        while (cursor.moveToNext()) {
            Log.d(TAG, "onCreate: " + cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
        }
        cursor.close();
    }
}