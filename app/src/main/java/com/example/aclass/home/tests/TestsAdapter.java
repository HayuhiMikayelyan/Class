package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.R;
import com.example.aclass.databinding.ItemRecyclerBinding;

import java.util.List;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestsViewHolder> {
    private final List<Test> tests;

    static class TestsViewHolder extends RecyclerView.ViewHolder{

        ItemRecyclerBinding binding;

        public TestsViewHolder(ItemRecyclerBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
    public TestsAdapter(List<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestsAdapter.TestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestsAdapter.TestsViewHolder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TestsAdapter.TestsViewHolder holder, int position) {
        holder.binding.tvTitle.setText(tests.get(position).getName());
        holder.binding.tvSubtitle.setText(tests.get(position).getQuestions().size()+" "+holder.itemView.getContext().getText(R.string.questions));
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }


}
