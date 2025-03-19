package com.example.pe_zalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView optionsTextView;
    private RecyclerView groupsRecyclerView;
    private GroupAdapter groupAdapter;

    // Tạo ActivityResultLauncher để nhận kết quả từ ContactSelectionActivity
    private final ActivityResultLauncher<Intent> createGroupLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Tải lại danh sách nhóm từ SQLite
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                List<Group> updatedGroupList = dbHelper.getAllGroups();
                groupAdapter = new GroupAdapter();
                for (Group group : updatedGroupList) {
                    groupAdapter.addGroup(group);
                }
                groupsRecyclerView.setAdapter(groupAdapter);
                Toast.makeText(this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show();
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsTextView = findViewById(R.id.optionsTextView);
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView);

        // Khởi tạo RecyclerView và adapter
        groupAdapter = new GroupAdapter();
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupsRecyclerView.setAdapter(groupAdapter);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
    List<Group> groupList = dbHelper.getAllGroups();
    for (Group group : groupList) {
        groupAdapter.addGroup(group);
    }

        // Thiết lập sự kiện click cho options menu
        optionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.create_group) {
                    // Mở ContactSelectionActivity với ActivityResultLauncher
                    Intent intent = new Intent(MainActivity.this, ContactSelectionActivity.class);
                    createGroupLauncher.launch(intent);
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}