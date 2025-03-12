package com.example.randomnumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CalculatorActivity extends AppCompatActivity {
    private TextInputEditText number1EditText, number2EditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        number1EditText = findViewById(R.id.number1EditText);
        number2EditText = findViewById(R.id.number2EditText);
        resultTextView = findViewById(R.id.resultTextView);

        MaterialButton addButton = findViewById(R.id.addButton);
        MaterialButton subtractButton = findViewById(R.id.subtractButton);
        MaterialButton multiplyButton = findViewById(R.id.multiplyButton);
        MaterialButton divideButton = findViewById(R.id.divideButton);

        addButton.setOnClickListener(v -> calculate(Operation.ADD));
        subtractButton.setOnClickListener(v -> calculate(Operation.SUBTRACT));
        multiplyButton.setOnClickListener(v -> calculate(Operation.MULTIPLY));
        divideButton.setOnClickListener(v -> calculate(Operation.DIVIDE));

        MaterialButton switchButton = findViewById(R.id.switchToRandomButton);
        switchButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng activity hiện tại để tránh stack quá nhiều
        });
    }

    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private void calculate(Operation operation) {
        String num1Str = number1EditText.getText().toString();
        String num2Str = number2EditText.getText().toString();

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double result = 0;

            switch (operation) {
                case ADD:
                    result = num1 + num2;
                    break;
                case SUBTRACT:
                    result = num1 - num2;
                    break;
                case MULTIPLY:
                    result = num1 * num2;
                    break;
                case DIVIDE:
                    if (num2 == 0) {
                        Toast.makeText(this, R.string.division_by_zero, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            String formattedResult = String.format("%.2f", result);
            resultTextView.setText(getString(R.string.result_format, formattedResult));

        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
        }
    }
} 