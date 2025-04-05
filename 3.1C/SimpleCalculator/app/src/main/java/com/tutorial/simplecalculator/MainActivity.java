package com.tutorial.simplecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText FirstNumber, SecondNumber;
    Button addButton, subtractButton;
    TextView Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        FirstNumber = findViewById(R.id.FirstNumber);
        SecondNumber = findViewById(R.id.SecondNumber);
        addButton = findViewById(R.id.addButton);
        subtractButton = findViewById(R.id.subtractButton);
        Result = findViewById(R.id.Result);

        addButton.setOnClickListener(v -> {
            String input1 = FirstNumber.getText().toString();
            String input2 = SecondNumber.getText().toString();

            if (input1.isEmpty() || input2.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else if (input1.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else if (input2.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Double n1 = Double.parseDouble(FirstNumber.getText().toString());
                Double n2 = Double.parseDouble(SecondNumber.getText().toString());

                Result.setText("Result: " + (n1 + n2));
            }


        });

        subtractButton.setOnClickListener(v -> {
            String input1 = FirstNumber.getText().toString();
            String input2 = SecondNumber.getText().toString();

            if (input1.isEmpty() || input2.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else if (input1.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else if (input2.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Double n1 = Double.parseDouble(FirstNumber.getText().toString());
                Double n2 = Double.parseDouble(SecondNumber.getText().toString());

                Result.setText("Result: " + (n1 - n2));
            }
        });
    }
}