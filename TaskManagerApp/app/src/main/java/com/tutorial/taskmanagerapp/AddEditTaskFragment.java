package com.tutorial.taskmanagerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddEditTaskFragment extends Fragment {
    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Button buttonAdd;
    private DatabaseHelper databaseHelper;
    private boolean isEditMode = false;
    private int taskId;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_task, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextDueDate = view.findViewById(R.id.editTextDueDate);
        buttonAdd = view.findViewById(R.id.buttonAdd);

        databaseHelper = new DatabaseHelper(getContext());
        calendar = Calendar.getInstance();

        // Set up date picker
        editTextDueDate.setOnClickListener(v -> showDatePicker());

        // Check if we're in edit mode
        if (getArguments() != null) {
            isEditMode = true;
            taskId = getArguments().getInt("id");
            String title = getArguments().getString("title");
            String description = getArguments().getString("description");
            String dueDate = getArguments().getString("dueDate");

            editTextTitle.setText(title);
            editTextDescription.setText(description);
            editTextDueDate.setText(dueDate);
            buttonAdd.setText("Update Task");
        }

        buttonAdd.setOnClickListener(v -> {
            if (isEditMode) {
                updateTask();
            } else {
                addTask();
            }

            navigateBackToTaskList();
        });


        return view;
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Format the date as YYYY-MM-DD
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = sdf.format(calendar.getTime());
                    editTextDueDate.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void addTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        Task task = new Task(title, description, dueDate);
        databaseHelper.addTask(task);

        Toast.makeText(getContext(), "Task added", Toast.LENGTH_SHORT).show();

        clearFields();
    }

    private void updateTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        Task task = new Task(title, description, dueDate);
        task.setId(taskId); // Set the existing ID for update
        databaseHelper.updateTask(task);

        Toast.makeText(getContext(), "Task updated", Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        editTextTitle.getText().clear();
        editTextDescription.getText().clear();
        editTextDueDate.getText().clear();
    }

    private void navigateBackToTaskList() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}