package com.example.th02app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnContactClickListener {
        void onContactClick(Contact contact, int position);
    }

    private final Context context;
    private final List<Contact> contactList;
    private final OnContactClickListener listener;

    public ContactAdapter(Context context, List<Contact> contactList, OnContactClickListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.tvAvatar.setText(contact.getAvatarInitial());
        int color = ContextCompat.getColor(context, contact.getAvatarColorRes());
        ViewCompat.setBackgroundTintList(holder.tvAvatar, ColorStateList.valueOf(color));

        holder.tvContactName.setText(contact.getName());
        holder.tvContactPhone.setText(contact.getPhone());
        holder.tvContactGroup.setText(contact.getGroup());

        // Áp dụng màu sắc badge riêng biệt cho từng nhóm
        String group = contact.getGroup() != null ? contact.getGroup() : "";
        if ("Bạn bè".equalsIgnoreCase(group)) {
            holder.tvContactGroup.setBackgroundResource(R.drawable.bg_badge_friend);
            holder.tvContactGroup.setTextColor(ContextCompat.getColor(context, R.color.group_friend_text));
        } else if ("Gia đình".equalsIgnoreCase(group)) {
            holder.tvContactGroup.setBackgroundResource(R.drawable.bg_badge_family);
            holder.tvContactGroup.setTextColor(ContextCompat.getColor(context, R.color.group_family_text));
        } else if ("Công việc".equalsIgnoreCase(group)) {
            holder.tvContactGroup.setBackgroundResource(R.drawable.bg_badge_work);
            holder.tvContactGroup.setTextColor(ContextCompat.getColor(context, R.color.group_work_text));
        } else {
            holder.tvContactGroup.setBackgroundResource(R.drawable.bg_badge_other);
            holder.tvContactGroup.setTextColor(ContextCompat.getColor(context, R.color.group_other_text));
        }

        if (contact.isFavorite()) {
            holder.ivFavoriteStar.setVisibility(View.VISIBLE);
        } else {
            holder.ivFavoriteStar.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContactClick(contact, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView tvAvatar;
        final TextView tvContactName;
        final TextView tvContactPhone;
        final TextView tvContactGroup;
        final ImageView ivFavoriteStar;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvContactPhone = itemView.findViewById(R.id.tvContactPhone);
            tvContactGroup = itemView.findViewById(R.id.tvContactGroup);
            ivFavoriteStar = itemView.findViewById(R.id.ivFavoriteStar);
        }
    }
}
