package com.example.randomnumber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button signInButton = findViewById(R.id.signInButton);
        TextView createAccountText = findViewById(R.id.createAccountText);

        signInButton.setOnClickListener(v -> validateAndSignIn());
        createAccountText.setOnClickListener(v -> {
            Intent intent = new Intent(FormActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void validateAndSignIn() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameInput.setError(getString(R.string.error_empty_field));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError(getString(R.string.error_empty_field));
            return;
        }

        // TODO: Implement actual sign in logic
        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
    }
} 