package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnItemClickListener listener;

    // Interface cho sự kiện click
    public interface OnItemClickListener {
        void onEditClick(User user, int position);
        void onDeleteClick(User user, int position);
    }

    public UserAdapter(List<User> userList, OnItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_advanced, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameTextView.setText("Username: " + user.getUsername());
        holder.fullnameTextView.setText("Fullname: " + user.getFullname());
        holder.emailTextView.setText("Email: " + user.getEmail());

        // Xử lý sự kiện edit và delete
        holder.editButton.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(user, position);
        });
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(user, position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user) {
        userList.add(user);
        notifyItemInserted(userList.size() - 1);
    }

    public void updateUser(int position, User user) {
        userList.set(position, user);
        notifyItemChanged(position);
    }

    public void deleteUser(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView, fullnameTextView, emailTextView;
        public TextView editButton, deleteButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
            fullnameTextView = itemView.findViewById(R.id.fullname);
            emailTextView = itemView.findViewById(R.id.email);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}