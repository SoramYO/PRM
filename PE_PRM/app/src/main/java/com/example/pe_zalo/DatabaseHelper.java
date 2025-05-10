package com.example.pe_zalo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chat_group_manager.db";
    private static final int DATABASE_VERSION = 3;

    // Bảng nhóm
    public static final String TABLE_GROUPS = "groups";
    public static final String COLUMN_GROUP_ID = "id";
    public static final String COLUMN_GROUP_NAME = "name";

    // Bảng liên hệ
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_CONTACT_ID = "id";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_PHONE = "phone";

    // Bảng thành viên
    public static final String TABLE_MEMBERS = "members";
    public static final String COLUMN_MEMBER_ID = "id";
    public static final String COLUMN_MEMBER_GROUP_ID = "group_id";
    public static final String COLUMN_MEMBER_CONTACT_ID = "contact_id"; // Thêm hằng số này

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

@Override
public void onCreate(SQLiteDatabase db) {
    // Tạo bảng nhóm
    String createGroupTable = "CREATE TABLE " + TABLE_GROUPS + " (" +
            COLUMN_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_GROUP_NAME + " TEXT)";
    db.execSQL(createGroupTable);

    // Tạo bảng liên hệ
    String createContactTable = "CREATE TABLE " + TABLE_CONTACTS + " (" +
            COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CONTACT_NAME + " TEXT, " +
            COLUMN_CONTACT_PHONE + " TEXT)";
    db.execSQL(createContactTable);

    // Tạo bảng thành viên (liên kết nhóm và liên hệ)
    String createMemberTable = "CREATE TABLE " + TABLE_MEMBERS + " (" +
            COLUMN_MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MEMBER_GROUP_ID + " INTEGER, " +
            COLUMN_MEMBER_CONTACT_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_MEMBER_GROUP_ID + ") REFERENCES " + TABLE_GROUPS + "(" + COLUMN_GROUP_ID + "), " +
            "FOREIGN KEY(" + COLUMN_MEMBER_CONTACT_ID + ") REFERENCES " + TABLE_CONTACTS + "(" + COLUMN_CONTACT_ID + "))";
    db.execSQL(createMemberTable);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        onCreate(db);
    }

    // Thêm một nhóm mới
    public long addGroup(String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUP_NAME, groupName);
        long groupId = db.insert(TABLE_GROUPS, null, values);
        db.close();
        return groupId;
    }

    // Thêm một liên hệ mới
    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());
        long contactId = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return contactId;
    }

    // Thêm một thành viên vào nhóm
    public void addMemberToGroup(long groupId, long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMBER_GROUP_ID, groupId);
        values.put(COLUMN_MEMBER_CONTACT_ID, contactId);
        db.insert(TABLE_MEMBERS, null, values);
        db.close();
    }

    // Lấy tất cả liên hệ
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(COLUMN_CONTACT_ID);
                    int nameIndex = cursor.getColumnIndex(COLUMN_CONTACT_NAME);
                    int phoneIndex = cursor.getColumnIndex(COLUMN_CONTACT_PHONE);

                    if (idIndex != -1 && nameIndex != -1 && phoneIndex != -1) {
                        int contactId = cursor.getInt(idIndex);
                        String contactName = cursor.getString(nameIndex);
                        String contactPhone = cursor.getString(phoneIndex);
                        Contact contact = new Contact(contactId, contactName, contactPhone);
                        contactList.add(contact);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error in getAllContacts: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return contactList;
    }

    // Lấy tất cả nhóm
public List<Group> getAllGroups() {
    List<Group> groupList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_GROUPS, null, null, null, null, null, null);

    if (cursor.moveToFirst()) {
        do {
            int idIndex = cursor.getColumnIndex(COLUMN_GROUP_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_GROUP_NAME);

            if (idIndex != -1 && nameIndex != -1) {
                long groupId = cursor.getLong(idIndex);
                String groupName = cursor.getString(nameIndex);
                List<Contact> members = getMembersByGroupId(groupId);
                Group group = new Group(groupName, members);
                group.setId(groupId); // THIS LINE IS CRITICAL - SET THE ID!
                groupList.add(group);
            }
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();
    return groupList;
}

    // Lấy thành viên theo groupId
    private List<Contact> getMembersByGroupId(long groupId) {
        List<Contact> members = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
String query = "SELECT c.* FROM " + TABLE_CONTACTS + " c " +
        "INNER JOIN " + TABLE_MEMBERS + " m ON c." + COLUMN_CONTACT_ID + " = m." + COLUMN_MEMBER_CONTACT_ID + " " +
        "WHERE m." + COLUMN_MEMBER_GROUP_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_CONTACT_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_CONTACT_NAME);
                int phoneIndex = cursor.getColumnIndex(COLUMN_CONTACT_PHONE);

                if (idIndex != -1 && nameIndex != -1 && phoneIndex != -1) {
                    int contactId = cursor.getInt(idIndex);
                    String contactName = cursor.getString(nameIndex);
                    String contactPhone = cursor.getString(phoneIndex);
                    Contact contact = new Contact(contactId, contactName, contactPhone);
                    members.add(contact);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return members;
    }
    // Add these methods to your DatabaseHelper class

// Cập nhật thông tin nhóm
public boolean updateGroup(long groupId, String newName) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_GROUP_NAME, newName);
    int rowsAffected = db.update(TABLE_GROUPS, values, COLUMN_GROUP_ID + " = ?", 
                                 new String[]{String.valueOf(groupId)});
    db.close();
    return rowsAffected > 0;
}

// Xóa một nhóm và các mối quan hệ thành viên
public boolean deleteGroup(long groupId) {
    SQLiteDatabase db = this.getWritableDatabase();
    // Xóa tất cả các thành viên của nhóm trước
    db.delete(TABLE_MEMBERS, COLUMN_MEMBER_GROUP_ID + " = ?", 
              new String[]{String.valueOf(groupId)});
    // Sau đó xóa nhóm
    int rowsAffected = db.delete(TABLE_GROUPS, COLUMN_GROUP_ID + " = ?", 
                                new String[]{String.valueOf(groupId)});
    db.close();
    return rowsAffected > 0;
}

// Xóa một thành viên khỏi nhóm
public boolean removeMemberFromGroup(long groupId, long contactId) {
    SQLiteDatabase db = this.getWritableDatabase();
    int rowsAffected = db.delete(TABLE_MEMBERS, 
                               COLUMN_MEMBER_GROUP_ID + " = ? AND " + 
                               COLUMN_MEMBER_CONTACT_ID + " = ?", 
                               new String[]{String.valueOf(groupId), String.valueOf(contactId)});
    db.close();
    return rowsAffected > 0;
}

// Lấy thông tin nhóm theo ID
public Group getGroupById(long groupId) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_GROUPS, null, COLUMN_GROUP_ID + " = ?", 
                            new String[]{String.valueOf(groupId)}, null, null, null);
    
    Group group = null;
    if (cursor != null && cursor.moveToFirst()) {
        int nameIndex = cursor.getColumnIndex(COLUMN_GROUP_NAME);
        if (nameIndex != -1) {
            String groupName = cursor.getString(nameIndex);
            List<Contact> members = getMembersByGroupId(groupId);
            group = new Group(groupName, members);
            group.setId(groupId); // We need to add this field to Group class
        }
    }
    
    if (cursor != null) cursor.close();
    db.close();
    return group;
}
}