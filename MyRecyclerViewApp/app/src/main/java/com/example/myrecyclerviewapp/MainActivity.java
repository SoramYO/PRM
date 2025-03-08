package com.example.myrecyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUsers = findViewById(R.id.rvUsers);

        userList = new ArrayList<>();
        userList.add(new User("NguyenTT", "Trần Thanh Tuyền", "nguyentt@gmail.com"));
        userList.add(new User("AnhTV", "Trần Văn Anh", "anhtv@gmail.com"));
        userList.add(new User("KhangTT", "Trần Thanh Khang", "khangtt@gmail.com"));
        userList.add(new User("HueHV", "Hứa Văn Huệ", "huehv@gmail.com"));


        userAdapter = new UserAdapter(userList);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        rvUsers.setAdapter(userAdapter);


    }
}
