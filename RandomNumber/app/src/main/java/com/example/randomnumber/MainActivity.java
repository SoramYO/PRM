package com.example.randomnumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private MaterialButton generateButton;
    private TextInputEditText minEditText, maxEditText;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        generateButton = findViewById(R.id.generateButton);
        minEditText = findViewById(R.id.minEditText);
        maxEditText = findViewById(R.id.maxEditText);
        random = new Random();

        generateButton.setOnClickListener(v -> generateRandomNumber());

        MaterialButton switchButton = findViewById(R.id.switchToCalculatorButton);
        switchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        });
    }

    private void generateRandomNumber() {
        String minStr = minEditText.getText().toString();
        String maxStr = maxEditText.getText().toString();

        if (minStr.isEmpty() || maxStr.isEmpty()) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);

            if (min >= max) {
                Toast.makeText(this, R.string.min_max_error, Toast.LENGTH_SHORT).show();
                return;
            }


            int randomNumber = random.nextInt(max - min + 1) + min;
            resultTextView.setText(String.valueOf(randomNumber));

        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
        }
    }
}