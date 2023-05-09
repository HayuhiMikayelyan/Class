package com.example.aclass.home.classes.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.databinding.ItemLessonsBinding;
import com.example.aclass.home.classes.models.Lesson;

import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonsViewHolder> {

    private final ArrayList<Lesson> lessons;

    public static class LessonsViewHolder extends RecyclerView.ViewHolder {
        ItemLessonsBinding binding;

        public LessonsViewHolder(ItemLessonsBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public LessonAdapter.LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LessonAdapter.LessonsViewHolder(ItemLessonsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonsViewHolder holder, int position) {
        holder.binding.tvTitle.setText(lessons.get(position).getTitle());
        holder.binding.tvDescription.setText(lessons.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public LessonAdapter(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

}
