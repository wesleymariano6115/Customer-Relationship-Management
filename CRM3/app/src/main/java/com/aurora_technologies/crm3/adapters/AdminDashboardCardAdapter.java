package com.aurora_technologies.crm3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.AdminDashboardCard;

import java.util.List;

public class AdminDashboardCardAdapter extends RecyclerView.Adapter<AdminDashboardCardAdapter.CardViewHolder> {

    public interface OnCardClickListener {
        void onCardClicked(AdminDashboardCard card);
    }

    private List<AdminDashboardCard> cardList;
    private OnCardClickListener listener;

    public AdminDashboardCardAdapter(List<AdminDashboardCard> cardList, OnCardClickListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_dashboard_card, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        AdminDashboardCard card = cardList.get(position);
        holder.tvTitle.setText(card.getTitle());
        holder.ivIcon.setImageResource(card.getIconResId());
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) listener.onCardClicked(card);
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            ivIcon = itemView.findViewById(R.id.ivCardIcon);
            cardView = itemView.findViewById(R.id.cardAdminDashboard);
        }
    }
}
