package com.example.randomnumber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView loginLink = findViewById(R.id.loginLink);

        signUpButton.setOnClickListener(v -> validateAndSignUp());
        loginLink.setOnClickListener(v -> finish());
    }

    private void validateAndSignUp() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (!validateUsername(username)) return;
        if (!validateEmail(email)) return;
        if (!validatePassword(password)) return;
        if (!validateConfirmPassword(password, confirmPassword)) return;

        // TODO: Implement actual sign up logic
        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            usernameInput.setError(getString(R.string.error_empty_field));
            return false;
        }
        if (username.length() < 3) {
            usernameInput.setError(getString(R.string.error_invalid_username));
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            emailInput.setError(getString(R.string.error_empty_field));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError(getString(R.string.error_invalid_email));
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError(getString(R.string.error_empty_field));
            return false;
        }
        if (password.length() < 6) {
            passwordInput.setError(getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String password, String confirmPassword) {
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordInput.setError(getString(R.string.error_empty_field));
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError(getString(R.string.error_password_mismatch));
            return false;
        }
        return true;
    }
} 