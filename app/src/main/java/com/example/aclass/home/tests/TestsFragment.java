package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.databinding.FragmentTestsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TestsFragment extends Fragment {

    private TestsAdapter adapter;
    private List<Test> tests;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTestsBinding binding = FragmentTestsBinding.inflate(inflater, container, false);

        tests = new ArrayList<>();
        adapter = new TestsAdapter(tests);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            loadData(getArguments().getString("category"));
        }


        return binding.getRoot();
    }

    private void loadData(String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tests").child(category);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Test test = dataSnapshot.getValue(Test.class);
                    if (test != null) {
                        tests.add(test);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}