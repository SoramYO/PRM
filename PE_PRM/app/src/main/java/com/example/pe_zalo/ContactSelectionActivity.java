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

public class ContactSelectionActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private Button createGroupButton;
    private EditText groupNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_selection);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        createGroupButton = findViewById(R.id.createGroupButton);
        groupNameEditText = findViewById(R.id.groupNameEditText);

        // Khởi tạo danh sách liên hệ từ cơ sở dữ liệu
        initializeContacts();

        // Thiết lập RecyclerView
        contactAdapter = new ContactAdapter(contactList);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactAdapter);

        // Thiết lập nút tạo nhóm
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Contact> selectedContacts = contactAdapter.getSelectedContacts();
                if (selectedContacts.size() > 0) {
                    createGroup(selectedContacts);
                } else {
                    Toast.makeText(ContactSelectionActivity.this,
                            "Vui lòng chọn ít nhất một liên hệ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeContacts() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        contactList = dbHelper.getAllContacts();
        if (contactList.isEmpty()) {
            List<Contact> sampleContacts = new ArrayList<>();
            sampleContacts.add(new Contact(0, "Nguyen Van A", "0901234567"));
            sampleContacts.add(new Contact(0, "Tran Thi B", "0912345678"));
            sampleContacts.add(new Contact(0, "Le Van C", "0923456789"));
            sampleContacts.add(new Contact(0, "Pham Thi D", "0934567890"));
            sampleContacts.add(new Contact(0, "Hoang Van E", "0945678901"));
            sampleContacts.add(new Contact(0, "Nguyen Thi F", "0956789012"));
            sampleContacts.add(new Contact(0, "Tran Van G", "0967890123"));
            sampleContacts.add(new Contact(0, "Le Thi H", "0978901234"));

            for (Contact contact : sampleContacts) {
                long contactId = dbHelper.addContact(contact);
                contact.setId((int) contactId);
            }
            contactList = sampleContacts;
        }
    }

    private void createGroup(List<Contact> selectedContacts) {
        String groupName = groupNameEditText.getText().toString().trim();

        if (groupName.isEmpty()) {
            groupName = Group.generateGroupName(selectedContacts);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long groupId = dbHelper.addGroup(groupName);

    if (groupId != -1) {
        for (Contact contact : selectedContacts) {
            dbHelper.addMemberToGroup(groupId, contact.getId());
        }

        Group newGroup = new Group(groupName, selectedContacts);
        newGroup.setId(groupId); // ADD THIS LINE to set the ID
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("group", newGroup);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Đã tạo nhóm: " + groupName, Toast.LENGTH_SHORT).show();
        finish();
    } else {
            Toast.makeText(this, "Lỗi khi tạo nhóm", Toast.LENGTH_SHORT).show();
        }
    }
}