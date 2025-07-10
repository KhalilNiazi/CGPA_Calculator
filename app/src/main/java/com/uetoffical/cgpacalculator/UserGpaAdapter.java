package com.uetoffical.cgpacalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserGpaAdapter extends RecyclerView.Adapter<UserGpaAdapter.UserViewHolder> {

    private final List<UserGpaRecord> userList;

    public UserGpaAdapter(List<UserGpaRecord> userList) {
        this.userList = userList;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRoll, tvDept, tvSemester, tvCgpa, tvTotalCH;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRoll = itemView.findViewById(R.id.tvRoll);
            tvDept = itemView.findViewById(R.id.tvDept);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvCgpa = itemView.findViewById(R.id.tvCgpa);
            tvTotalCH = itemView.findViewById(R.id.tvTotalCH);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_gpa, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserGpaRecord user = userList.get(position);
        holder.tvName.setText("Name: " + user.getName());
        holder.tvRoll.setText("Roll: " + user.getRoll());
        holder.tvCgpa.setText("CGPA: " + String.format("%.2f", user.getCgpa()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
