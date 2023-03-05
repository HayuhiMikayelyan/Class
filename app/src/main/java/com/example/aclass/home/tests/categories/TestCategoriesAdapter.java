package com.example.aclass.home.tests.categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.R;
import com.example.aclass.databinding.ItemTestCategoriesBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TestCategoriesAdapter extends RecyclerView.Adapter<TestCategoriesAdapter.TestCategoriesViewHolder> {

    private final List<Category> categories;
    private final Context context;


    static class TestCategoriesViewHolder extends RecyclerView.ViewHolder {

        ItemTestCategoriesBinding binding;

        public TestCategoriesViewHolder(ItemTestCategoriesBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public TestCategoriesAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @NonNull
    @Override
    public TestCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestCategoriesViewHolder(ItemTestCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TestCategoriesViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.tvCategory.setText(category.getName());
        holder.binding.tvTestCount.setText(category.getCount() +" "+context.getText(R.string.tests));
        Picasso.with(context).load(category.getIcon()).into(holder.binding.icCategory);

        holder.itemView.setOnClickListener((View v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("category",category.getName());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_testsFragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
