package com.uetoffical.cgpacalculator;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    RecyclerView recyclerUserList;
    List<UserGpaRecord> userList;
    UserGpaAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        recyclerUserList = findViewById(R.id.recyclerUserList);
        recyclerUserList.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();
        adapter = new UserGpaAdapter(userList);
        recyclerUserList.setAdapter(adapter);

        loadAllUsers();
    }

    private void loadAllUsers() {
        db.collectionGroup("gpa_records")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("name");
                        String roll = doc.getString("roll");
                        double cgpa = doc.getDouble("cgpa") != null ? doc.getDouble("cgpa") : 0.0;

                        userList.add(new UserGpaRecord(name, roll, cgpa));
                    }
                    adapter.notifyDataSetChanged();
                });
    }

}
