package com.aurora_technologies.crm3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora_technologies.crm3.R;
import com.aurora_technologies.crm3.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface UserActionListener {
        void onEditUser(User user);
        void onDeleteUser(User user);
    }

    private List<User> userList;
    private UserActionListener listener;

    public UserAdapter(List<User> userList, UserActionListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText(user.getRole());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditUser(user);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteUser(user);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmail, tvRole;
        ImageButton btnEdit, btnDelete;
        CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            tvRole = itemView.findViewById(R.id.tvUserRole);
            btnEdit = itemView.findViewById(R.id.btnEditUser);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
            cardView = itemView.findViewById(R.id.cardUser);
        }
    }
}
