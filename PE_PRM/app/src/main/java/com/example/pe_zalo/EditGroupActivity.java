package com.example.pe_zalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditGroupActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> allContacts;
    private List<Contact> currentGroupMembers;
    private Button saveGroupButton;
    private EditText groupNameEditText;
    private long groupId;
    private int groupPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        saveGroupButton = findViewById(R.id.saveGroupButton);
        groupNameEditText = findViewById(R.id.groupNameEditText);

        // Get group ID and position from intent
        groupId = getIntent().getLongExtra("group_id", -1);
        groupPosition = getIntent().getIntExtra("group_position", -1);

        if (groupId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy nhóm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load group data
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Group group = dbHelper.getGroupById(groupId);
        
        if (group == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy nhóm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set current group name
        groupNameEditText.setText(group.getName());
        
        // Store current group members
        currentGroupMembers = group.getMembers();

        // Load all contacts
        loadContacts();

        // Set up RecyclerView
        contactAdapter = new ContactAdapter(allContacts);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactAdapter);

        // Pre-select current group members
        for (Contact contact : allContacts) {
            for (Contact member : currentGroupMembers) {
                if (contact.getId() == member.getId()) {
                    contact.setSelected(true);
                    break;
                }
            }
        }
        contactAdapter.notifyDataSetChanged();

        // Set up save button
        saveGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGroup();
            }
        });
    }

    private void loadContacts() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        allContacts = dbHelper.getAllContacts();
    }

    private void updateGroup() {
        String groupName = groupNameEditText.getText().toString().trim();
        if (groupName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Contact> selectedContacts = contactAdapter.getSelectedContacts();
        if (selectedContacts.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một liên hệ", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        
        // Update group name
        boolean nameUpdated = dbHelper.updateGroup(groupId, groupName);
        
        // Update members
        // First remove all existing members
        for (Contact member : currentGroupMembers) {
            dbHelper.removeMemberFromGroup(groupId, member.getId());
        }
        
        // Then add selected members
        for (Contact contact : selectedContacts) {
            dbHelper.addMemberToGroup(groupId, contact.getId());
        }

        if (nameUpdated) {
            Toast.makeText(this, "Cập nhật nhóm thành công", Toast.LENGTH_SHORT).show();
            
            // Return result to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("group_position", groupPosition);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật nhóm", Toast.LENGTH_SHORT).show();
        }
    }
}