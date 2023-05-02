package com.example.aclass.home.classes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.basic.MainActivity;
import com.example.aclass.databinding.CodeAlertDialogBinding;
import com.example.aclass.databinding.FragmentCreateClassBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CreateClassFragment extends Fragment {

    private String subject;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentCreateClassBinding binding = FragmentCreateClassBinding.inflate(inflater, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.drop_down_item,
                getResources().getStringArray(R.array.spinner_items));

        binding.filledExposed.setAdapter(adapter);
        binding.filledExposed.setOnItemClickListener((adapterView, view, i, l) -> subject = adapterView.getItemAtPosition(i).toString());

        binding.btnCreateClass.setOnClickListener(view -> {
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            String className = Objects.requireNonNull(binding.edtClassName.getEditText()).getText().toString();
            if (!className.equals("") && subject!=null) {

                FirebaseFirestore store = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                DocumentReference documentReference = store.collection("classes").document(id);

                Map<String, Object> classes = new HashMap<>();
                classes.put("id", id);
                classes.put("className", className);
                classes.put("members", 1);
                classes.put("subject", subject);

                documentReference.set(classes).addOnSuccessListener(unused -> {
                    if (auth.getCurrentUser() != null) {
                        DocumentReference documentReference1 = store.collection("users").document(auth.getCurrentUser().getUid());
                        documentReference1.update("classes", FieldValue.arrayUnion(id));
                    }
                });

            } else {
                Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}