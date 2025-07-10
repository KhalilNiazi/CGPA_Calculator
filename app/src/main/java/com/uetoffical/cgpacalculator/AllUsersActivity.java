package com.uetoffical.cgpacalculator;

import android.os.Bundle;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.*;

public class AllUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerUserList;
    private List<UserGpaRecord> userList, filteredList;
    private UserGpaAdapter adapter;
    private FirebaseFirestore db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        recyclerUserList = findViewById(R.id.recyclerUserList);
        searchView = findViewById(R.id.searchView);

        recyclerUserList.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new UserGpaAdapter(filteredList);
        recyclerUserList.setAdapter(adapter);

        loadAllUsers();
        setupSearch();
    }

    private void loadAllUsers() {
        db.collectionGroup("gpa_records")
                .get()
                .addOnSuccessListener(snapshots -> {
                    userList.clear();

                    for (QueryDocumentSnapshot doc : snapshots) {
                        String name = doc.getString("name");
                        String roll = doc.getString("roll");
                        String dept = doc.getString("department");
                        String semester = doc.getString("semester");
                        double cgpa = doc.getDouble("cgpa") != null ? doc.getDouble("cgpa") : 0.0;
                        int credits = doc.getLong("totalCredits") != null ? doc.getLong("totalCredits").intValue() : 0;

                        // Add every record as a separate entry
                        userList.add(new UserGpaRecord(name, roll, dept, semester, cgpa, credits));
                    }

                    filteredList.clear();
                    filteredList.addAll(userList);
                    adapter.notifyDataSetChanged();
                });
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String query) {
                String key = query.toLowerCase(Locale.ROOT);
                filteredList.clear();
                for (UserGpaRecord u : userList) {
                    if ((u.getName() != null && u.getName().toLowerCase().contains(key)) ||
                            (u.getRoll() != null && u.getRoll().toLowerCase().contains(key)) ||
                            (u.getDepartment() != null && u.getDepartment().toLowerCase().contains(key)) ||
                            (u.getSemester() != null && u.getSemester().toLowerCase().contains(key))) {
                        filteredList.add(u);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
