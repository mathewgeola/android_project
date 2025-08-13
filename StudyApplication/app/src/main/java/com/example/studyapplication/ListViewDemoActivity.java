package com.example.studyapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.databinding.ActivityListViewDemoBinding;

import java.util.ArrayList;

public class ListViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityListViewDemoBinding binding = ActivityListViewDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            strings.add("元素" + i);
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_item, strings);
//        binding.listview.setAdapter(adapter);

        ArrayList<ContactPerson> contactPersons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ContactPerson contactPerson = new ContactPerson("联系人" + i, R.mipmap.ic_launcher);
            contactPersons.add(contactPerson);
        }
        ArrayAdapter<ContactPerson> adapter = new ContactPersonListViewAdapter(this, R.layout.contact_person_item, contactPersons);
        binding.listview.setAdapter(adapter);

    }
}