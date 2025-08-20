package com.example.studyapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.databinding.ActivityServiceDemoBinding;
import com.example.studyapplication.service.BindService;
import com.example.studyapplication.service.ForegroundService;
import com.example.studyapplication.service.OrdinaryService;

public class ServiceDemoActivity extends AppCompatActivity {
    private ActivityServiceDemoBinding binding;

    private BindService.LocalBinder binder;

    private boolean bound = false;

    private static final String TAG = "ServiceDemoActivity";

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BindService.LocalBinder) service;
            binder.startWork();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityServiceDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.startOrdinaryServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceDemoActivity.this, OrdinaryService.class);
            startService(intent);
        });

        binding.stopOrdinaryServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceDemoActivity.this, OrdinaryService.class);
            stopService(intent);
        });

        binding.startBindServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceDemoActivity.this, BindService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);
        });

        binding.getWorkProcessBtn.setOnClickListener(v -> {
            if (binder != null) {
                Log.d(TAG, "onClick: " + binder.getWorkProcess());
            }
        });

        binding.stopBindServiceBtn.setOnClickListener(v -> {
            if (bound) {
                unbindService(connection);
                bound = false;
            }
        });

        binding.startForegroundServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceDemoActivity.this, ForegroundService.class);
            startService(intent);
        });

        binding.stopForegroundServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceDemoActivity.this, ForegroundService.class);
            stopService(intent);
        });

    }
}