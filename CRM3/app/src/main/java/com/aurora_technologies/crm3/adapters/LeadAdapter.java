package com.aurora_technologies.crm3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.Lead;

import java.util.List;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.LeadViewHolder> {

    private List<Lead> leadList;

    public LeadAdapter(List<Lead> leadList) {
        this.leadList = leadList;
    }

    @NonNull
    @Override
    public LeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lead, parent, false);
        return new LeadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadViewHolder holder, int position) {
        Lead lead = leadList.get(position);
        holder.tvName.setText(lead.getName());
        holder.tvStatus.setText(lead.getStatus());
        holder.tvSource.setText(lead.getSource());
        holder.tvLastContact.setText(lead.getLastContactDate());
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    static class LeadViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStatus, tvSource, tvLastContact;
        CardView cardView;

        public LeadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvLeadName);
            tvStatus = itemView.findViewById(R.id.tvLeadStatus);
            tvSource = itemView.findViewById(R.id.tvLeadSource);
            tvLastContact = itemView.findViewById(R.id.tvLeadLastContact);
            cardView = itemView.findViewById(R.id.cardLead);
        }
    }
}
