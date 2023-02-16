package com.example.aclass.auth;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentRegisterBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseFirestore store;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        binding.btnRegister.setOnClickListener(this::PerformAuth);

        return binding.getRoot();
    }

    private void PerformAuth(View view) {
        String name = Objects.requireNonNull(binding.edtName.getEditText()).getText().toString();
        String email = Objects.requireNonNull(binding.edtEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(binding.edtPassword.getEditText()).getText().toString();
        String repeat_password = Objects.requireNonNull(binding.edtRepeatPassword.getEditText()).getText().toString();

        boolean isTeacher = false;
        if (getArguments() != null) {
            isTeacher = RegisterFragmentArgs.fromBundle(getArguments()).getIsTeacher();
        }

        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";

        if (name.isEmpty()){
            binding.edtName.setError(getString(R.string.wrong_name));
        } else if (!email.matches(emailPattern)) {
            binding.edtName.setError(null);
            binding.edtEmail.setError(getString(R.string.wrong_email));
        } else if (password.isEmpty() || password.length() < 6) {
            binding.edtEmail.setError(null);
            binding.edtPassword.setError(getString(R.string.wrong_password));
        } else if (!password.equals(repeat_password)) {
            binding.edtEmail.setError(null);
            binding.edtPassword.setError(null);
            binding.edtRepeatPassword.setError(getString(R.string.different_password));
        } else {
            binding.edtEmail.setError(null);
            binding.edtPassword.setError(null);
            binding.edtRepeatPassword.setError(null);
            showProgress();

            boolean finalIsTeacher = isTeacher;
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                dismissProgress();
                if (task.isSuccessful()) {
                    DocumentReference documentReference = store.collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid());

                    Map<String, Object> user = new HashMap<>();
                    user.put("name",name);
                    user.put("email",email);
                    user.put("isTeacher", finalIsTeacher);
                    user.put("id", auth.getCurrentUser().getUid());

                    documentReference.set(user).addOnSuccessListener(unused -> Toast.makeText(requireContext(), R.string.successful, Toast.LENGTH_LONG).show());

                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task1 -> {
                        if (task.isSuccessful()){
                            if (finalIsTeacher){
                                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_createClassFragment);
                            } else {
                                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_joinToClassFragment);
                            }
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), R.string.failure, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProgress(){
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setTitle(getString(R.string.registration));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void dismissProgress(){
        progressDialog.dismiss();
    }

}