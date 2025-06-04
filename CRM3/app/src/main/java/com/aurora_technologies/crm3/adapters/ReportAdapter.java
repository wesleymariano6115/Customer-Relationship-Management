package com.aurora_technologies.crm3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Report> reportList;

    public ReportAdapter(List<Report> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reportList.get(position);
        holder.tvReportTitle.setText(report.getTitle());
        holder.tvReportDate.setText(report.getDate());

        holder.btnView.setOnClickListener(v -> {
            // TODO: Implement view report
        });

        holder.btnDownload.setOnClickListener(v -> {
            // TODO: Implement download report
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvReportTitle, tvReportDate;
        Button btnView, btnDownload;
        CardView cardView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReportTitle = itemView.findViewById(R.id.tvReportTitle);
            tvReportDate = itemView.findViewById(R.id.tvReportDate);
            btnView = itemView.findViewById(R.id.btnViewReport);
            btnDownload = itemView.findViewById(R.id.btnDownloadReport);
            cardView = itemView.findViewById(R.id.cardReport);
        }
    }
}
