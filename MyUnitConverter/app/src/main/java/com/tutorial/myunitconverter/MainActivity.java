package com.tutorial.myunitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Spinner categorySpinner, inputUnit, outputUnit;
    TextView result;
    String selectedCategory = "Length"; // Default category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        input = findViewById(R.id.input);
        categorySpinner = findViewById(R.id.categorySpinner);
        inputUnit = findViewById(R.id.inputUnit);
        outputUnit = findViewById(R.id.outputUnit);
        result = findViewById(R.id.result);

        // Categories
        String[] categories = {"Length", "Weight", "Temperature"};
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        // Set listeners
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
                updateUnitSpinners();
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Input unit changes
        inputUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Output unit changes
        outputUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Input number changes
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                convert();
            }
        });

        updateUnitSpinners();
    }

    // When selecting categories
    private void updateUnitSpinners() {
        String[] lengthUnits = {"km", "m", "cm", "mm", "mile", "yard", "foot", "inch"};
        String[] weightUnits = {"kg", "g", "mg", "lb", "oz"};
        String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

        String[] units;
        if (selectedCategory.equals("Length")) {
            units = lengthUnits;
        } else if (selectedCategory.equals("Weight")) {
            units = weightUnits;
        } else {
            units = tempUnits;
        }

        inputUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units));
        outputUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units));
    }

    private void convert() {
        if (input.getText().toString().isEmpty()) {
            result.setText("Converted Value");
            return;
        }

        double inputValue = Double.parseDouble(input.getText().toString());
        String fromUnit = inputUnit.getSelectedItem().toString();
        String toUnit = outputUnit.getSelectedItem().toString();

        double outputValue = 0.0;

        if (selectedCategory.equals("Length")) {
            outputValue = convertLength(inputValue, fromUnit, toUnit);
        } else if (selectedCategory.equals("Weight")) {
            outputValue = convertWeight(inputValue, fromUnit, toUnit);
        } else if (selectedCategory.equals("Temperature")) {
            outputValue = convertTemperature(inputValue, fromUnit, toUnit);
        }

        result.setText(String.valueOf(outputValue));
    }

    private double convertLength(double value, String from, String to) {
        double meters;
        switch (from) {
            case "km":
                meters = value * 1000; // If the same unit, cancelled out the multiplication
                break;
            case "cm":
                meters = value / 100;
                break;
            case "mm":
                meters = value / 1000;
                break;
            case "mile":
                meters = value * 1609.34;
                break;
            case "yard":
                meters = value * 0.9144;
                break;
            case "foot":
                meters = value * 0.3048;
                break;
            case "inch":
                meters = value * 0.0254;
                break;
            default:
                meters = value;
                break;
        }

        switch (to) {
            case "km":
                return meters / 1000; // If the same unit, cancelled out the multiplication
            case "cm":
                return meters * 100;
            case "mm":
                return meters * 1000;
            case "mile":
                return meters / 1609.34;
            case "yard":
                return meters / 0.9144;
            case "foot":
                return meters / 0.3048;
            case "inch":
                return meters / 0.0254;
            default:
                return meters;
        }
    }

    private double convertWeight(double value, String from, String to) {
        double grams;
        switch (from) {
            case "kg":
                grams = value * 1000; // If the same unit, cancelled out the multiplication
                break;
            case "mg":
                grams = value / 1000;
                break;
            case "lb":
                grams = value * 453.592;
                break;
            case "oz":
                grams = value * 28.3495;
                break;
            default:
                grams = value;
                break;
        }

        switch (to) {
            case "kg":
                return grams / 1000; // If the same unit, cancelled out the multiplication
            case "mg":
                return grams * 1000;
            case "lb":
                return grams / 453.592;
            case "oz":
                return grams / 28.3495;
            default:
                return grams;
        }
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value; // Returning the same value if the same
        if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 1.8) + 32;
        if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) * 0.56;
        if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15;
        if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15;
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) return ((value - 32) * 0.56) + 273.15;
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) return ((value - 273.15) * 1.8) + 32;
        return value;
    }
}