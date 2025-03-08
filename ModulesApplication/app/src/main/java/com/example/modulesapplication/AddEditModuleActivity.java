package com.example.modulesapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditModuleActivity extends AppCompatActivity {
    private ImageView ivModuleImage;
    private EditText etTitle, etDescription;
    private RadioGroup rgPlatform;
    private RadioButton rbAndroid, rbIOS;
    private Button btnSave, btnSelectImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int selectedImageResId = R.drawable.ic_launcher_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_module);

        initializeViews();
        setupImageSelection();
        loadExistingData();
        setupSaveButton();
    }

    private void initializeViews() {
        ivModuleImage = findViewById(R.id.ivModuleImage);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        rgPlatform = findViewById(R.id.rgPlatform);
        rbAndroid = findViewById(R.id.rbAndroid);
        rbIOS = findViewById(R.id.rbIOS);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);
    }

    private void setupImageSelection() {
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }

    private void loadExistingData() {
        Intent intent = getIntent();
        if (intent != null) {
            etTitle.setText(intent.getStringExtra("title"));
            etDescription.setText(intent.getStringExtra("description"));
            String platform = intent.getStringExtra("platform");
            selectedImageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_background);
            ivModuleImage.setImageResource(selectedImageResId);

            if ("iOS".equals(platform)) {
                rbIOS.setChecked(true);
            } else {
                rbAndroid.setChecked(true);
            }
        }
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                saveModule();
            }
        });
    }

    private boolean validateInput() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Vui lòng nhập tiêu đề");
            return false;
        }

        if (TextUtils.isEmpty(description)) {
            etDescription.setError("Vui lòng nhập mô tả");
            return false;
        }

        return true;
    }

    private void saveModule() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String platform = rbIOS.isChecked() ? "iOS" : "Android";
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("platform", platform);
        resultIntent.putExtra("imageResId", selectedImageResId);
        
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ivModuleImage.setImageURI(selectedImage);
            // For this example, we'll still use a drawable resource
            selectedImageResId = R.drawable.ic_launcher_background;
        }
    }
}
