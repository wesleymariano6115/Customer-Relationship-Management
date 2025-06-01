// Location: app/src/main/java/com/example/front_end_ui/DashboardFragment.java
package com.example.front_end_ui; // <--- CHANGE THIS LINE to match your project's package

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView recentActivitiesRecyclerView;
    private RecentActivitiesAdapter recentActivitiesAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recentActivitiesRecyclerView = view.findViewById(R.id.recent_activities_recycler_view);
        recentActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Replace with actual data from your backend
        List<String> activities = new ArrayList<>();
        activities.add("Call with John Smith");
        activities.add("Meeting: ABC Corp");
        activities.add("Follow-up on XYZ proposal");
        activities.add("Update Q3 Sales Report");

        recentActivitiesAdapter = new RecentActivitiesAdapter(activities);
        recentActivitiesRecyclerView.setAdapter(recentActivitiesAdapter);
    }

    // RecentActivitiesAdapter is an inner static class, so it's defined here.
    private static class RecentActivitiesAdapter extends RecyclerView.Adapter<RecentActivitiesAdapter.ActivityViewHolder> {

        private final List<String> activities;

        public RecentActivitiesAdapter(List<String> activities) {
            this.activities = activities;
        }

        @NonNull
        @Override
        public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate your item_recent_activity.xml here
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_activity, parent, false);
            return new ActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
            String activity = activities.get(position);
            holder.activityTextView.setText(activity);
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }

        static class ActivityViewHolder extends RecyclerView.ViewHolder {
            TextView activityTextView;

            public ActivityViewHolder(@NonNull View itemView) {
                super(itemView);
                activityTextView = itemView.findViewById(R.id.activity_description_text_view);
            }
        }
    }
}