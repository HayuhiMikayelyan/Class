package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.aclass.R;
import com.example.aclass.databinding.FragmentClassesBinding;
import com.example.aclass.auth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClassesFragment extends Fragment {
    private FragmentClassesBinding binding;
    private Boolean isTeacher = false;
    private ClassesAdapter adapter;
    private List<Class> classes;
    private List<String> classIds;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);

        classIds = Collections.singletonList("");
        classes = new ArrayList<>();

        adapter = new ClassesAdapter(classes);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);

        binding.btnAddClass.setOnClickListener(v -> {
            if (!isTeacher) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_joinToClassFragment);
            } else {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_createClassFragment);
            }
        });

        getClasses();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getClasses() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        db.collection("users").addSnapshotListener((value, error) -> {
            if (value!=null){
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    User user = documentChange.getDocument().toObject(User.class);
                    if (user.getId().equals(id)) {
                        isTeacher = user.getIsTeacher();
                        classIds = user.getClasses();
                    }
                    if (Objects.equals(classIds.get(0), "")) {
                        binding.tvNoClasses.setVisibility(View.VISIBLE);
                        binding.imgNoClasses.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvNoClasses.setVisibility(View.GONE);
                        binding.imgNoClasses.setVisibility(View.GONE);
                        db.collection("classes").addSnapshotListener((value1, error1) -> {
                            if (value1 != null) {
                                for (DocumentChange documentChange1 : value1.getDocumentChanges()) {
                                    Class class1 = documentChange1.getDocument().toObject(Class.class);
                                    for (String classId : classIds) {
                                        if (class1.getId().equals(classId)) {
                                            classes.add(class1);
                                        }
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(requireContext(), getText(R.string.try_again), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(requireContext(), getText(R.string.try_again), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
