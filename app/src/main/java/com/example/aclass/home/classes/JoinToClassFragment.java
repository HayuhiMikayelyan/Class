package com.example.aclass.home.classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.basic.MainActivity;
import com.example.aclass.databinding.FragmentJoinToClassBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JoinToClassFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentJoinToClassBinding binding = FragmentJoinToClassBinding.inflate(inflater, container, false);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        binding.btnJoin.setOnClickListener(v -> {
            String id = Objects.requireNonNull(binding.edtCode.getEditText()).getText().toString();
            showProgress();
            Task<QuerySnapshot> task = firestore.collection("classes")
                    .whereEqualTo("id", id)
                    .get();
            task.addOnCompleteListener(task1 -> {
                dismissProgress();
                if (task1.getResult().isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.wrong_code), Toast.LENGTH_SHORT).show();
                    binding.edtCode.getEditText().setText("");
                } else {
                    DocumentReference documentReference1 = firestore.collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    documentReference1.update("classes", FieldValue.arrayUnion(id));
                    firestore.collection("classes").document(id).update("members",FieldValue.increment(1));
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
            });
        });

        binding.btnLater.setOnClickListener(v -> {
            showProgress();
            DocumentReference documentReference1 = firestore.collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
            Map<String, List<String>> users = new HashMap<>();
            users.put("classes", Collections.singletonList(""));
            documentReference1.set(users, SetOptions.merge()).addOnCompleteListener(task2 -> {
                dismissProgress();
                if (task2.isSuccessful()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), getString(R.string.try_again), Toast.LENGTH_LONG).show();
                }
            });
        });

        return binding.getRoot();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setTitle(getString(R.string.join));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void dismissProgress() {
        progressDialog.dismiss();
    }
}