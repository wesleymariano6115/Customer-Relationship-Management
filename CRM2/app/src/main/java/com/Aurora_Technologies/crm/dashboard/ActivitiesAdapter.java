package com.Aurora_Technologies.crm.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.Aurora_Technologies.crm.R;
import com.Aurora_Technologies.crm.dashboard.models.ActivityItem;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {

    private List<ActivityItem> activityList = new ArrayList<>();

    public void setActivityList(List<ActivityItem> activities) {
        this.activityList = activities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityItem item = activityList.get(position);
        holder.icon.setText(item.getIcon());
        holder.text.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView icon, text;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.activityIcon);
            text = itemView.findViewById(R.id.activityText);
        }
    }
}
