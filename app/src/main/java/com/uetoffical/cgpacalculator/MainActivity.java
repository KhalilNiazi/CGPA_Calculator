package com.uetoffical.cgpacalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TableLayout courseTable;
    Button btnCalculate, btnClear, btnViewProfile;
    TextView addMoreCourses, cgpaResult, tvUserInfo;
    EditText etPriorGpa, etPriorCredits;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        courseTable = findViewById(R.id.courseTable);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        addMoreCourses = findViewById(R.id.addMoreCourses);
        etPriorGpa = findViewById(R.id.etPriorGpa);
        etPriorCredits = findViewById(R.id.etPriorCredits);
    //    btnViewProfile = findViewById(R.id.btnViewProfile);
        cgpaResult = findViewById(R.id.cgpaResult);
        tvUserInfo = findViewById(R.id.tvUserInfo);

        Button btnAllUsers = findViewById(R.id.btnAllUsers);
        btnAllUsers.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AllUsersActivity.class));
        });

        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (prefs.getBoolean("isFirstTime", true)) {
            showWelcomeDialog();
        } else {
            showUserBanner();
        }

        /*btnViewProfile.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
            String name = sp.getString("name", "N/A");
            String roll = sp.getString("roll", "N/A");
            String semester = sp.getString("semester", "N/A");
            String department = sp.getString("department", "N/A");

            new AlertDialog.Builder(this)
                    .setTitle("Your Profile")
                    .setMessage("Name: " + name + "\nRoll: " + roll + "\nSemester: " + semester + "\nDepartment: " + department)
                    .setPositiveButton("OK", null)
                    .show();
        });*/

        addCourseRow();
        addMoreCourses.setOnClickListener(v -> addCourseRow());

        btnCalculate.setOnClickListener(v -> calculateCGPA());
        btnClear.setOnClickListener(v -> {
            courseTable.removeAllViews();
            addCourseRow();
            etPriorCredits.setText("");
            etPriorGpa.setText("");
            cgpaResult.setText("");
        });
    }

    private void showWelcomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome to CGPA Calculator");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_welcome, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.etName);
        EditText etRoll = view.findViewById(R.id.etRoll);
        EditText etSemester = view.findViewById(R.id.etSemester);
        EditText etDept = view.findViewById(R.id.etDept);

        builder.setCancelable(false);
        builder.setPositiveButton("Save", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dlg -> {
            Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btn.setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String roll = etRoll.getText().toString().trim();
                String semester = etSemester.getText().toString().trim();
                String dept = etDept.getText().toString().trim();

                if (name.isEmpty() || roll.isEmpty() || semester.isEmpty() || dept.isEmpty()) {
                    etName.setError("Required");
                    etRoll.setError("Required");
                    etSemester.setError("Required");
                    etDept.setError("Required");
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.putString("roll", roll);
                    editor.putString("semester", semester);
                    editor.putString("department", dept);
                    editor.putBoolean("isFirstTime", false);
                    editor.apply();
                    showUserBanner();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    private void showUserBanner() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name = prefs.getString("name", "N/A");
        String roll = prefs.getString("roll", "N/A");
        String sem = prefs.getString("semester", "N/A");
        String dept = prefs.getString("department", "N/A");

        tvUserInfo.setText("Welcome, " + name + " (" + roll + ") - " + dept + " | Sem: " + sem);
    }

    private void addCourseRow() {
        View row = LayoutInflater.from(this).inflate(R.layout.course_row, null);
        Spinner spinner = row.findViewById(R.id.spGrade);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        courseTable.addView(row);
    }

    private void calculateCGPA() {
        double totalCredits = 0, totalPoints = 0;
        for (int i = 0; i < courseTable.getChildCount(); i++) {
            View row = courseTable.getChildAt(i);
            EditText etCredit = row.findViewById(R.id.etCredit);
            Spinner spGrade = row.findViewById(R.id.spGrade);

            String creditStr = etCredit.getText().toString().trim();
            String grade = spGrade.getSelectedItem().toString().trim();
            if (creditStr.isEmpty() || grade.equals("-")) continue;

            double credit = Double.parseDouble(creditStr);
            double gradePoint = getGradePoint(grade);
            totalCredits += credit;
            totalPoints += credit * gradePoint;
        }

        String priorGpaStr = etPriorGpa.getText().toString().trim();
        String priorCreditStr = etPriorCredits.getText().toString().trim();
        if (!priorGpaStr.isEmpty() && !priorCreditStr.isEmpty()) {
            double priorGpa = Double.parseDouble(priorGpaStr);
            double priorCredits = Double.parseDouble(priorCreditStr);
            totalPoints += priorGpa * priorCredits;
            totalCredits += priorCredits;
        }

        double cgpa = (totalCredits == 0) ? 0.0 : totalPoints / totalCredits;
        cgpaResult.setText("Your CGPA: " + String.format("%.2f", cgpa));

        saveCGPAToFirebase(cgpa, totalCredits);
    }

    private double getGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A+": return 4.0;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "D-": return 0.7;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    private void saveCGPAToFirebase(double cgpa, double totalCredits) {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name = prefs.getString("name", "N/A");
        String roll = prefs.getString("roll", "N/A");
        String semester = prefs.getString("semester", "N/A");
        String department = prefs.getString("department", "N/A");

        Map<String, Object> gpaData = new HashMap<>();
        gpaData.put("name", name);
        gpaData.put("roll", roll);
        gpaData.put("semester", semester);
        gpaData.put("department", department);
        gpaData.put("cgpa", cgpa);
        gpaData.put("totalCredits", totalCredits);
        gpaData.put("timestamp", System.currentTimeMillis());

        db.collection("users")
                .document(roll)
                .collection("gpa_records")
                .add(gpaData);
    }
}
