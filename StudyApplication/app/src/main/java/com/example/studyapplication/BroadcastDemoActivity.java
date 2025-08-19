package com.example.studyapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.studyapplication.broadcast.DynamicBroadcastReceiver;
import com.example.studyapplication.broadcast.NormalBroadcastReceiver;
import com.example.studyapplication.broadcast.OrderedOneBroadcastReceiver;
import com.example.studyapplication.broadcast.OrderedThreeBroadcastReceiver;
import com.example.studyapplication.broadcast.OrderedTwoBroadcastReceiver;
import com.example.studyapplication.databinding.ActivityBroadcastDemoBinding;

public class BroadcastDemoActivity extends AppCompatActivity {
    private static final String NORMAL_ACTION = BuildConfig.APPLICATION_ID + ".action.NORMAL";
    private static final String ORDERED_ACTION = BuildConfig.APPLICATION_ID + ".action.ORDERED";

    private ActivityBroadcastDemoBinding binding;

    private DynamicBroadcastReceiver dynamicBroadcastReceiver;

    private NormalBroadcastReceiver normalBroadcastReceiver;

    private NormalBroadcastReceiver localBroadcastManagerNormalBroadcastReceiver;

    private OrderedOneBroadcastReceiver orderedOneBroadcastReceiver;
    private OrderedTwoBroadcastReceiver orderedTwoBroadcastReceiver;
    private OrderedThreeBroadcastReceiver orderedThreeBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_broadcast_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityBroadcastDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(dynamicBroadcastReceiver, intentFilter);

        initView();

        initNormalBroadcastReceiver();

        initOrderedBroadcastReceiver();

        initLocalBroadcastManagerNormalBroadcastReceiver();
    }

    private void initView() {
        binding.normalBroadcastReceiverBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(NORMAL_ACTION);
            sendBroadcast(intent);
        });

        binding.orderedBroadcastReceiverBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(ORDERED_ACTION);
            sendOrderedBroadcast(intent, null);
        });

        binding.localBroadcastManagerNormalBroadcastReceiverBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(NORMAL_ACTION);
            LocalBroadcastManager.getInstance(BroadcastDemoActivity.this).sendBroadcast(intent);
        });
    }

    private void initNormalBroadcastReceiver() {
        normalBroadcastReceiver = new NormalBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NORMAL_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(normalBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        }
    }

    private void initOrderedBroadcastReceiver() {
        orderedOneBroadcastReceiver = new OrderedOneBroadcastReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(ORDERED_ACTION);
        intentFilter1.setPriority(2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(orderedOneBroadcastReceiver, intentFilter1, Context.RECEIVER_EXPORTED);
        }

        orderedTwoBroadcastReceiver = new OrderedTwoBroadcastReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ORDERED_ACTION);
        intentFilter2.setPriority(3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(orderedTwoBroadcastReceiver, intentFilter2, Context.RECEIVER_EXPORTED);
        }

        orderedThreeBroadcastReceiver = new OrderedThreeBroadcastReceiver();
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction(ORDERED_ACTION);
        intentFilter3.setPriority(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(orderedThreeBroadcastReceiver, intentFilter3, Context.RECEIVER_EXPORTED);
        }
    }

    private void initLocalBroadcastManagerNormalBroadcastReceiver() {
        localBroadcastManagerNormalBroadcastReceiver = new NormalBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NORMAL_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastManagerNormalBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dynamicBroadcastReceiver != null) {
            unregisterReceiver(dynamicBroadcastReceiver);
        }

        if (normalBroadcastReceiver != null) {
            unregisterReceiver(normalBroadcastReceiver);
        }

        if (orderedOneBroadcastReceiver != null) {
            unregisterReceiver(orderedOneBroadcastReceiver);
        }
        if (orderedTwoBroadcastReceiver != null) {
            unregisterReceiver(orderedTwoBroadcastReceiver);
        }
        if (orderedThreeBroadcastReceiver != null) {
            unregisterReceiver(orderedThreeBroadcastReceiver);
        }

        if (localBroadcastManagerNormalBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastManagerNormalBroadcastReceiver);
        }
    }
}