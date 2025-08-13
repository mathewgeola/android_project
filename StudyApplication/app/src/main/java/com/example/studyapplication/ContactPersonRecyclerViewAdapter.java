package com.example.studyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactPersonRecyclerViewAdapter extends RecyclerView.Adapter<ContactPersonRecyclerViewAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarImageView;

        private final TextView nameTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            avatarImageView = view.findViewById(R.id.avatar_imageview);
            nameTextView = view.findViewById(R.id.name_textview);
        }
    }

    private List<ContactPerson> contactPersons;

    public ContactPersonRecyclerViewAdapter(List<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_person_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactPerson contactPerson = contactPersons.get(position);
        holder.avatarImageView.setImageResource(contactPerson.getAvatar());
        holder.nameTextView.setText(contactPerson.getName());
    }

    @Override
    public int getItemCount() {
        return contactPersons.size();
    }
}
