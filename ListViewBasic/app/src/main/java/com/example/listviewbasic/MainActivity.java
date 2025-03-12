package com.example.listviewbasic;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.widget.Spinner;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView previewImage;
    private Uri selectedImageUri;
    private ArrayList<Fruit> fruits;
    private FruitAdapter adapter;
    private ListView listView;

    // Mảng chứa các resource drawable mặc định của Android
    private final int[] fruitImages = {
        android.R.drawable.ic_menu_gallery,  // Placeholder cho hình ảnh
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_help,
        android.R.drawable.ic_menu_info_details,
        android.R.drawable.ic_menu_view
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fruits = new ArrayList<>();
        
        // Khởi tạo adapter
        adapter = new FruitAdapter(this, fruits);
        listView.setAdapter(adapter);

        // Xử lý sự kiện click item
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showOptionsDialog(position);
            return true;
        });

        // Xử lý sự kiện click FAB để thêm mới
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm mới");

        previewImage = dialogView.findViewById(R.id.previewImage);
        Button btnChooseImage = dialogView.findViewById(R.id.btnChooseImage);
        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText descriptionEditText = dialogView.findViewById(R.id.descriptionEditText);

        btnChooseImage.setOnClickListener(v -> openImageChooser());

        builder.setView(dialogView);
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (!title.isEmpty() && !description.isEmpty() && selectedImageUri != null) {
                Bitmap bitmap = loadBitmapFromUri(selectedImageUri);
                // Lưu bitmap vào internal storage và lấy URI
                String imagePath = saveImageToInternalStorage(bitmap);
                
                fruits.add(new Fruit(title, description, imagePath));
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Đã thêm mới!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            previewImage.setImageURI(selectedImageUri);
        }
    }

    private Bitmap loadBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String fileName = "img_" + System.currentTimeMillis() + ".jpg";
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showEditDialog(final int position) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa");

        previewImage = dialogView.findViewById(R.id.previewImage);
        Button btnChooseImage = dialogView.findViewById(R.id.btnChooseImage);
        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText descriptionEditText = dialogView.findViewById(R.id.descriptionEditText);

        Fruit fruit = fruits.get(position);
        titleEditText.setText(fruit.getTitle());
        descriptionEditText.setText(fruit.getDescription());

        // Load ảnh hiện tại
        try {
            FileInputStream fis = openFileInput(fruit.getImagePath());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            previewImage.setImageBitmap(bitmap);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            previewImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        btnChooseImage.setOnClickListener(v -> openImageChooser());

        builder.setView(dialogView);
        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                if (selectedImageUri != null) {
                    Bitmap bitmap = loadBitmapFromUri(selectedImageUri);
                    String imagePath = saveImageToInternalStorage(bitmap);
                    fruits.get(position).setImagePath(imagePath);
                }
                fruits.get(position).setTitle(title);
                fruits.get(position).setDescription(description);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showOptionsDialog(final int position) {
        String[] options = {"Sửa", "Xóa"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hành động");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                showEditDialog(position);
            } else {
                showDeleteConfirmDialog(position);
            }
        });
        builder.show();
    }

    private void showDeleteConfirmDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa item này?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            fruits.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}