package com.example.aclass.home.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.auth.User;
import com.example.aclass.databinding.ItemMembersBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {

    private final List<User> members;
    private final Context context;

    public static class MembersViewHolder extends RecyclerView.ViewHolder {
        ItemMembersBinding binding;

        public MembersViewHolder(ItemMembersBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public MembersAdapter(List<User> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @NonNull
    @Override
    public MembersAdapter.MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MembersAdapter.MembersViewHolder(ItemMembersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MembersAdapter.MembersViewHolder holder, int position) {
        holder.binding.tvName.setText(members.get(position).getName());
        holder.binding.tvEmail.setText(members.get(position).getEmail());
        Picasso.with(context).load(members.get(position).getIcon()).into(holder.binding.img);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }


}
