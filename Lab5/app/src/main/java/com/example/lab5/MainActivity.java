package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private EditText etUsername, etFullname, etEmail;

// In MainActivity.java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_advanced);

    recyclerView = findViewById(R.id.recyclerView);
    etUsername = findViewById(R.id.et_username);
    etFullname = findViewById(R.id.et_fullname);
    etEmail = findViewById(R.id.et_email);
    Button btnAdd = findViewById(R.id.btn_add);
    Button btnSwitchToBasic = findViewById(R.id.btn_switch_to_basic);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    userList = new ArrayList<>();
    userAdapter = new UserAdapter(userList, this);
    recyclerView.setAdapter(userAdapter);

    // Add initial users (optional)
    userList.add(new User("NguyenTT", "Tran Thanh Nguyen", "nguyentt@fpt.edu.vn"));
    userList.add(new User("Antv", "Tran Van An", "antv@gmail.com"));
    userAdapter.notifyDataSetChanged();

    // Add button functionality
    btnAdd.setOnClickListener(v -> {
        // Existing add user code
    });

    // Add switch to basic activity functionality
    btnSwitchToBasic.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, MainBasic.class);
        startActivity(intent);
    });
}

    @Override
    public void onEditClick(User user, int position) {
        // Hiển thị dialog hoặc EditText để cập nhật thông tin
        etUsername.setText(user.getUsername());
        etFullname.setText(user.getFullname());
        etEmail.setText(user.getEmail());

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setText("Update User");
        btnAdd.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String fullname = etFullname.getText().toString();
            String email = etEmail.getText().toString();
            if (!username.isEmpty() && !fullname.isEmpty() && !email.isEmpty()) {
                userAdapter.updateUser(position, new User(username, fullname, email));
                etUsername.setText("");
                etFullname.setText("");
                etEmail.setText("");
                btnAdd.setText("Add User");
                btnAdd.setOnClickListener(w -> {
                    // Khôi phục hành vi thêm người dùng
                    String newUsername = etUsername.getText().toString();
                    String newFullname = etFullname.getText().toString();
                    String newEmail = etEmail.getText().toString();
                    if (!newUsername.isEmpty() && !newFullname.isEmpty() && !newEmail.isEmpty()) {
                        userAdapter.addUser(new User(newUsername, newFullname, newEmail));
                        etUsername.setText("");
                        etFullname.setText("");
                        etEmail.setText("");
                    }
                });
            }
        });
    }

    @Override
    public void onDeleteClick(User user, int position) {
        userAdapter.deleteUser(position);
    }
}