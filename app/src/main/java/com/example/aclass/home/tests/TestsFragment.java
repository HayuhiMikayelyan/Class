package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentTestsBinding;
import com.example.aclass.home.classes.Class;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class TestsFragment extends Fragment {

    private List<Category> categoryList;
    private TestCategoriesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTestsBinding binding = FragmentTestsBinding.inflate(inflater, container, false);

        categoryList = new ArrayList<>();

        adapter = new TestCategoriesAdapter(categoryList,requireContext());

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recycler.setAdapter(adapter);

        getCategories();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategories() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").addSnapshotListener((value, error) -> {
            if (value!=null){
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    categoryList.add(documentChange.getDocument().toObject(Category.class));
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(requireContext(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            }
        });
    }
}