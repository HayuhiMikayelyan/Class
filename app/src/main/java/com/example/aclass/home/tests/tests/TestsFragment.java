package com.example.aclass.home.tests.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentTestsBinding;
import com.example.aclass.home.classes.models.Class;
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
    private String categoryS;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTestsBinding binding = FragmentTestsBinding.inflate(inflater, container, false);

        tests = new ArrayList<>();

        if (getArguments() != null) {
            categoryS = getArguments().getString("category");
            loadData(getArguments().getString("category"));
        }

        Class aClass = getArguments().getParcelable("class");
        ArrayList<Test> lessonTests = (ArrayList<Test>) getArguments().getSerializable("tests");

        adapter = new TestsAdapter(tests, categoryS, aClass,getArguments().getString("title"),getArguments().getString("description"),lessonTests);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setHasFixedSize(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        binding.btnAddTest.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("category", categoryS);
            Navigation.findNavController(v).navigate(R.id.action_testsFragment_to_addTestFragment, bundle);
        });

        return binding.getRoot();
    }

    private void filter(String newText) {
        List<Test> filteredTest = new ArrayList<>();

        for (Test test : tests) {
            if (test.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredTest.add(test);
            }
        }

        adapter.filterList(filteredTest);
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