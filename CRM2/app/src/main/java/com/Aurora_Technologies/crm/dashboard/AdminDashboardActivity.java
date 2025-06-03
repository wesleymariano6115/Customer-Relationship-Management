package com.Aurora_Technologies.crm.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Aurora_Technologies.crm.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminDashboardActivity extends AppCompatActivity {

    private AdminDashboardViewModel viewModel;

    // UI components
    private TextView valueSales, subtitleSales;
    private TextView valueTopRep, subtitleTopRep;
    private TextView valueNewCustomers, subtitleNewCustomers;
    private TextView valueNewLeads, subtitleNewLeads;

    private BarChart barChartLeadsStatus;
    private PieChart pieChartLeadSources;

    private RecyclerView recyclerViewActivities;
    private ActivitiesAdapter activitiesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setupUI();
        setupViewModel();
        observeData();
    }

    private void setupUI() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            // TODO: Handle hamburger menu click
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_notifications) {
                // TODO: Handle notifications click
                return true;
            }
            return false;
        });

        // Metrics cards values
        valueSales = findViewById(R.id.valueSales);
        subtitleSales = findViewById(R.id.subtitleSales);

        valueTopRep = findViewById(R.id.valueTopRep);
        subtitleTopRep = findViewById(R.id.subtitleTopRep);

        valueNewCustomers = findViewById(R.id.valueNewCustomers);
        subtitleNewCustomers = findViewById(R.id.subtitleNewCustomers);

        valueNewLeads = findViewById(R.id.valueNewLeads);
        subtitleNewLeads = findViewById(R.id.subtitleNewLeads);

        // Bar chart
        barChartLeadsStatus = findViewById(R.id.barChartLeadsStatus);
        setupBarChart();

        // Pie chart
        pieChartLeadSources = findViewById(R.id.pieChartLeadSources);
        setupPieChart();

        // Activities RecyclerView
        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        activitiesAdapter = new ActivitiesAdapter();
        recyclerViewActivities.setAdapter(activitiesAdapter);

        // FAB
        FloatingActionButton fab = findViewById(R.id.fabAddLead);
        fab.setOnClickListener(v -> {
            // TODO: Handle add lead action
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(AdminDashboardViewModel.class);
    }

    private void observeData() {
        viewModel.getMetrics().observe(this, metrics -> {
            if (metrics != null) {
                valueSales.setText(String.format("₱%,d", metrics.getSales()));
                subtitleSales.setText("+8% from last month"); // Placeholder, ideally dynamic

                valueTopRep.setText(metrics.getTopRep());
                subtitleTopRep.setText(metrics.getTopRepLeadsConverted() + " leads converted");

                valueNewCustomers.setText(String.valueOf(metrics.getNewCustomers()));
                subtitleNewCustomers.setText("in last 7 days");

                valueNewLeads.setText(String.valueOf(metrics.getNewLeads()));
                subtitleNewLeads.setText("this week");
            }
        });

        viewModel.getLeadStatus().observe(this, leadStatusMap -> {
            if (leadStatusMap != null) {
                updateBarChart(leadStatusMap);
            }
        });

        viewModel.getLeadSource().observe(this, leadSourceMap -> {
            if (leadSourceMap != null) {
                updatePieChart(leadSourceMap);
            }
        });

        viewModel.getActivities().observe(this, activities -> {
            if (activities != null) {
                activitiesAdapter.setActivityList(activities);
            }
        });
    }

    private void setupBarChart() {
        barChartLeadsStatus.getDescription().setEnabled(false);
        barChartLeadsStatus.setDrawGridBackground(false);
        barChartLeadsStatus.setDrawBarShadow(false);
        barChartLeadsStatus.setDrawValueAboveBar(true);
        barChartLeadsStatus.setMaxVisibleValueCount(50);
        barChartLeadsStatus.setPinchZoom(false);
        barChartLeadsStatus.setDrawGridBackground(false);
        barChartLeadsStatus.setBackgroundColor(getResources().getColor(R.color.background_dark));

        // X-axis
        XAxis xAxis = barChartLeadsStatus.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.text_primary));
        xAxis.setGranularity(1f);

        // Y-axis left
        barChartLeadsStatus.getAxisLeft().setTextColor(getResources().getColor(R.color.text_primary));
        barChartLeadsStatus.getAxisLeft().setAxisMinimum(0f);
        barChartLeadsStatus.getAxisLeft().setAxisMaximum(20f);

        // Y-axis right disabled
        barChartLeadsStatus.getAxisRight().setEnabled(false);

        // Legend
        Legend legend = barChartLeadsStatus.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(getResources().getColor(R.color.text_primary));
    }

    private void updateBarChart(Map<String, Integer> leadStatusMap) {
        // X-axis labels in order
        final String[] statuses = {"New", "Contacted", "Follow-Up", "Converted", "Lost"};

        List<BarEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        for (int i = 0; i < statuses.length; i++) {
            int count = leadStatusMap.getOrDefault(statuses[i].toLowerCase(), 0);
            entries.add(new BarEntry(i, count));

            switch (statuses[i]) {
                case "New":
                    colors.add(getResources().getColor(R.color.blue_500));
                    break;
                case "Contacted":
                    colors.add(getResources().getColor(R.color.orange_500));
                    break;
                case "Follow-Up":
                    colors.add(getResources().getColor(R.color.green_500));
                    break;
                case "Converted":
                    colors.add(getResources().getColor(R.color.teal_200));
                    break;
                case "Lost":
                    colors.add(getResources().getColor(R.color.red_500));
                    break;
                default:
                    colors.add(getResources().getColor(R.color.text_primary));
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Lead Status");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(getResources().getColor(R.color.text_primary));
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        barChartLeadsStatus.setData(data);

        // Set X-axis labels formatter
        barChartLeadsStatus.getXAxis().setValueFormatter(new XAxis.ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value >= 0 && value < statuses.length) {
                    return statuses[(int) value];
                }
                return "";
            }
        });

        barChartLeadsStatus.invalidate();
    }

    private void setupPieChart() {
        pieChartLeadSources.getDescription().setEnabled(false);
        pieChartLeadSources.setDrawHoleEnabled(true);
        pieChartLeadSources.setHoleColor(getResources().getColor(R.color.background_dark));
        pieChartLeadSources.setTransparentCircleRadius(61f);
        pieChartLeadSources.setEntryLabelColor(getResources().getColor(R.color.text_primary));
        pieChartLeadSources.setEntryLabelTextSize(12f);
        pieChartLeadSources.setCenterTextColor(getResources().getColor(R.color.text_primary));
        pieChartLeadSources.setCenterTextSize(16f);

        Legend legend = pieChartLeadSources.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextColor(getResources().getColor(R.color.text_primary));
    }

    private void updatePieChart(Map<String, Integer> leadSourceMap) {
        List<PieEntry> entries = new ArrayList<>();
        int totalCount = 0;

        for (Map.Entry<String, Integer> entry : leadSourceMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), capitalize(entry.getKey())));
            totalCount += entry.getValue();
        }

        PieDataSet dataSet = new PieDataSet(entries, "Lead Sources");

        // Distinct vivid colors
        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.blue_500));
        colors.add(getResources().getColor(R.color.green_500));
        colors.add(getResources().getColor(R.color.orange_500));
        colors.add(getResources().getColor(R.color.teal_200));
        dataSet.setColors(colors);

        dataSet.setValueTextColor(getResources().getColor(R.color.text_primary));
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChartLeadSources.setData(data);

        pieChartLeadSources.setCenterText("Total\n" + totalCount);

        pieChartLeadSources.invalidate();
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}
