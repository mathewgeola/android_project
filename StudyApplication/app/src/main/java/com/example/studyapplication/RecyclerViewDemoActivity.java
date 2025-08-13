package com.example.studyapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studyapplication.databinding.ActivityRecyclerViewDemoBinding;

import java.util.ArrayList;

public class RecyclerViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityRecyclerViewDemoBinding binding = ActivityRecyclerViewDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<ContactPerson> contactPersons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ContactPerson contactPerson = new ContactPerson("联系人" + i, R.mipmap.ic_launcher);
            contactPersons.add(contactPerson);
        }
        ContactPersonRecyclerViewAdapter adapter = new ContactPersonRecyclerViewAdapter(contactPersons);
        binding.recyclerview.setAdapter(adapter);

    }
}