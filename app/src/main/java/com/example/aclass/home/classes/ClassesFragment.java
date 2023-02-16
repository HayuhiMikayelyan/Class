package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.aclass.databinding.FragmentClassesBinding;
import com.example.aclass.home.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassesFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private List<String> classIds;
    private List<Class> classes;
    private ClassesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentClassesBinding binding = FragmentClassesBinding.inflate(inflater, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        classes = new ArrayList<>();
        classIds = new ArrayList<>();

        adapter = new ClassesAdapter(requireContext(), classes);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);

        eventChangeListener();


        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void eventChangeListener() {
        String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        db.collection("users").addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    User user = documentChange.getDocument().toObject(User.class);
                    if (user.getId().equals(id)) {
                        classIds = user.getClasses();
                    }
                }
                db.collection("classes").addSnapshotListener((value1, error1) -> {
                    if (value1 != null) {
                        for (DocumentChange documentChange : value1.getDocumentChanges()) {
                            Class class1 = documentChange.getDocument().toObject(Class.class);
                            for (String classId : classIds) {
                                if (class1.getId().equals(classId)) {
                                    classes.add(class1);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}