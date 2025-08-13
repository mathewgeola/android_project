package com.example.studyapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactPersonListViewAdapter extends ArrayAdapter<ContactPerson> {

    private final int resource;

    public ContactPersonListViewAdapter(@NonNull Context context, int resource, @NonNull List<ContactPerson> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        ContactPerson contactPerson = getItem(position);
        if (contactPerson != null) {
            ImageView imageView = view.findViewById(R.id.avatar_imageview);
            imageView.setImageResource(contactPerson.getAvatar());

            TextView textView = view.findViewById(R.id.name_textview);
            textView.setText(contactPerson.getName());
        }
        return view;
    }
}
