package com.example.modulesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ModuleAdapter.OnModuleClickListener {
    private RecyclerView recyclerView;
    private ModuleAdapter adapter;
    private List<Module> moduleList;

    private static final int REQUEST_CODE_ADD = 100;
    private static final int REQUEST_CODE_EDIT = 101;
    private int editingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         recyclerView = findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

         // Khởi tạo danh sách với dữ liệu mẫu
         moduleList = new ArrayList<>();
         moduleList.add(new Module(R.drawable.ic_launcher_background, "Module 1", "Mô tả 1", "Android"));
         moduleList.add(new Module(R.drawable.ic_launcher_background, "Module 2", "Mô tả 2", "iOS"));

         adapter = new ModuleAdapter(this, moduleList, this);
         recyclerView.setAdapter(adapter);

         FloatingActionButton fab = findViewById(R.id.fab);
         fab.setOnClickListener(v -> {
             // Mở Activity để thêm mới Module
             Intent intent = new Intent(MainActivity.this, AddEditModuleActivity.class);
             startActivityForResult(intent, REQUEST_CODE_ADD);
         });
    }

    @Override
    public void onModuleEdit(Module module, int position) {
        editingPosition = position;
        Intent intent = new Intent(this, AddEditModuleActivity.class);
        intent.putExtra("title", module.getTitle());
        intent.putExtra("description", module.getDescription());
        intent.putExtra("platform", module.getPlatform());
        intent.putExtra("imageResId", module.getImageResId());
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onModuleDelete(int position) {
        moduleList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Đã xóa module", Toast.LENGTH_SHORT).show();
    }

    // Nhận dữ liệu trả về từ Activity thêm/sửa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK && data != null) {
             String title = data.getStringExtra("title");
             String description = data.getStringExtra("description");
             String platform = data.getStringExtra("platform");
             int imageResId = data.getIntExtra("imageResId", R.drawable.ic_launcher_background);
             if (requestCode == REQUEST_CODE_ADD) {
                 Module newModule = new Module(imageResId, title, description, platform);
                 moduleList.add(newModule);
                 adapter.notifyItemInserted(moduleList.size() - 1);
             } else if (requestCode == REQUEST_CODE_EDIT) {
                 Module module = moduleList.get(editingPosition);
                 module.setTitle(title);
                 module.setDescription(description);
                 module.setPlatform(platform);
                 module.setImageResId(imageResId);
                 adapter.notifyItemChanged(editingPosition);
             }
         }
    }
}
