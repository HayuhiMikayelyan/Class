package com.example.aclass.home.tests.categories;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentTestsCategoriesBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TestCategoriesFragment extends Fragment {

    private List<Category> categoryList;
    private TestCategoriesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTestsCategoriesBinding binding = FragmentTestsCategoriesBinding.inflate(inflater, container, false);

        categoryList = new ArrayList<>();

        adapter = new TestCategoriesAdapter(categoryList, requireContext());

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recycler.setAdapter(adapter);

        getCategories();


        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategories() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    categoryList.add(documentChange.getDocument().toObject(Category.class));
                }
                categoryList.sort(Comparator.comparingInt(Category::getId));
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(requireContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            }
        });
    }
}