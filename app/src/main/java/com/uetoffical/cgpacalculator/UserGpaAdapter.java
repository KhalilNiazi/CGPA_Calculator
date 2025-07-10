package com.uetoffical.cgpacalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserGpaAdapter extends RecyclerView.Adapter<UserGpaAdapter.GpaViewHolder> {

    private final List<UserGpaRecord> userList;

    public UserGpaAdapter(List<UserGpaRecord> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public GpaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_gpa, parent, false);
        return new GpaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GpaViewHolder holder, int position) {
        UserGpaRecord user = userList.get(position);

        holder.tvName.setText(user.getName());
        holder.tvRoll.setText("Roll: " + user.getRoll());
        holder.tvDept.setText("Department: " + user.getDepartment());
        holder.tvSemester.setText("Semester: " + user.getSemester());
        holder.tvCgpa.setText(String.format("CGPA: %.2f", user.getCgpa()));
        holder.tvCredits.setText("Credits: " + user.getCredits());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class GpaViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRoll, tvDept, tvSemester, tvCgpa, tvCredits;

        public GpaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName     = itemView.findViewById(R.id.tvName);
            tvRoll     = itemView.findViewById(R.id.tvRoll);
            tvDept     = itemView.findViewById(R.id.tvDept);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvCgpa     = itemView.findViewById(R.id.tvCgpa);
            tvCredits  = itemView.findViewById(R.id.tvCredits);
        }
    }
}
