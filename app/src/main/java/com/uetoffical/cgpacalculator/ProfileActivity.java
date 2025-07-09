package com.uetoffical.cgpacalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText etName, etRoll, etSemester, etDept;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etRoll = findViewById(R.id.etRoll);
        etSemester = findViewById(R.id.etSemester);
        etDept = findViewById(R.id.etDept);
        btnSave = findViewById(R.id.btnSaveProfile);

        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        etName.setText(prefs.getString("name", ""));
        etRoll.setText(prefs.getString("roll", ""));
        etSemester.setText(prefs.getString("semester", ""));
        etDept.setText(prefs.getString("department", ""));

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String roll = etRoll.getText().toString().trim();
            String semester = etSemester.getText().toString().trim();
            String dept = etDept.getText().toString().trim();

            if (name.isEmpty() || roll.isEmpty() || semester.isEmpty() || dept.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", name);
            editor.putString("roll", roll);
            editor.putString("semester", semester);
            editor.putString("department", dept);
            editor.apply();

            // Save to Firestore
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("name", name);
            userMap.put("roll", roll);
            userMap.put("semester", semester);
            userMap.put("department", dept);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(roll)
                    .set(userMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
