package com.example.aclass.home.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.databinding.ItemClassesBinding;

import java.util.List;


public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesViewHolder> {
    private final List<Class> classes;

    static class ClassesViewHolder extends RecyclerView.ViewHolder{

        ItemClassesBinding binding;

        public ClassesViewHolder(ItemClassesBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
    public ClassesAdapter(Context context, List<Class> classes) {
        this.classes = classes;
    }



    @NonNull
    @Override
    public ClassesAdapter.ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassesViewHolder(ItemClassesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesAdapter.ClassesViewHolder holder, int position) {
        holder.binding.tvClassname.setText(classes.get(position).getClassName());
        holder.binding.tvSubject.setText(classes.get(position).getSubject());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }


}
