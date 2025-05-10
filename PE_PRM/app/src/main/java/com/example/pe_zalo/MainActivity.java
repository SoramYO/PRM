package com.example.pe_zalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.MenuItem;
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
    public static final int MENU_EDIT_GROUP = 1;
    public static final int MENU_DELETE_GROUP = 2;
    // Tạo ActivityResultLauncher để nhận kết quả từ ContactSelectionActivity
private final ActivityResultLauncher<Intent> createGroupLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                // DON'T CREATE A NEW ADAPTER! Just update the current one
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                List<Group> updatedGroupList = dbHelper.getAllGroups();
                groupAdapter.setGroups(updatedGroupList);
                Toast.makeText(this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        private final ActivityResultLauncher<Intent> editGroupLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                int position = result.getData().getIntExtra("group_position", -1);
                if (position != -1) {
                    DatabaseHelper dbHelper = new DatabaseHelper(this);
                    List<Group> updatedGroupList = dbHelper.getAllGroups();
                    groupAdapter.setGroups(updatedGroupList);
                    Toast.makeText(this, "Nhóm đã được cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsTextView = findViewById(R.id.optionsTextView);
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView);
    registerForContextMenu(groupsRecyclerView);
        // Khởi tạo RecyclerView và adapter
        groupAdapter = new GroupAdapter();
    groupAdapter.setOnGroupItemClickListener(new GroupAdapter.OnGroupItemClickListener() {
        @Override
        public void onGroupItemClick(Group group, int position) {
            Toast.makeText(MainActivity.this, "Nhóm: " + group.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGroupItemLongClick(Group group, int position) {
            // Position is tracked in adapter's onLongClick
        }
    });
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
@Override
public boolean onContextItemSelected(MenuItem item) {
    int position = groupAdapter.getContextMenuPosition();
    
    if (position < 0) {
        Toast.makeText(this, "Invalid position", Toast.LENGTH_SHORT).show();
        return false;
    }
    
    Group selectedGroup = groupAdapter.getGroupAt(position);
    if (selectedGroup == null) {
        Toast.makeText(this, "Group not found", Toast.LENGTH_SHORT).show();
        return false;
    }
    
    switch (item.getItemId()) {
        case MENU_EDIT_GROUP:
            // Launch edit group activity
            Intent editIntent = new Intent(MainActivity.this, EditGroupActivity.class);
            editIntent.putExtra("group_id", selectedGroup.getId());
            editIntent.putExtra("group_position", position);
            editGroupLauncher.launch(editIntent);
            return true;
            
        case MENU_DELETE_GROUP:
            // Show delete confirmation dialog
            showDeleteGroupDialog(selectedGroup, position);
            return true;
            
        default:
            return super.onContextItemSelected(item);
    }
}

    private void showDeleteGroupDialog(Group group, int position) {
    new AlertDialog.Builder(this)
        .setTitle("Xóa nhóm")
        .setMessage("Bạn có chắc chắn muốn xóa nhóm " + group.getName() + "?")
        .setPositiveButton("Xóa", (dialog, which) -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            boolean success = dbHelper.deleteGroup(group.getId());
            if (success) {
                groupAdapter.removeGroup(position);
                Toast.makeText(this, "Đã xóa nhóm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể xóa nhóm", Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("Hủy", null)
        .show();
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