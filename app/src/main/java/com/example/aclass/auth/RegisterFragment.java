package com.example.aclass.auth;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentRegisterBinding;
import com.example.aclass.databinding.VerifyEmailDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (name.isEmpty()) {
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
                    FirebaseUser fUser = auth.getCurrentUser();
                    Objects.requireNonNull(fUser).sendEmailVerification().addOnSuccessListener(unused -> {
                        Dialog dialog = new Dialog(requireContext());
                        VerifyEmailDialogBinding binding1 = VerifyEmailDialogBinding.inflate(LayoutInflater.from(requireContext()));

                        binding1.btnOk.setOnClickListener(v -> {
                            dialog.dismiss();

                            DocumentReference documentReference = store.collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid());

                            Map<String, Object> user = new HashMap<>();
                            Map<String, Integer> test = new HashMap<>();
                            user.put("name", name);
                            user.put("email", email);
                            user.put("isTeacher", finalIsTeacher);
                            user.put("id", auth.getCurrentUser().getUid());
                            user.put("test", test);
                            user.put("icon", "https://firebasestorage.googleapis.com/v0/b/class-32ca7.appspot.com/o/images%2Fprofile%20picture.png?alt=media&token=d5f369bc-8d4e-44fa-9f88-11fab1dd0f85");

                            documentReference.set(user).addOnSuccessListener(unused1 -> Toast.makeText(requireContext(), R.string.successful, Toast.LENGTH_LONG).show());

                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()) {
                                    if (finalIsTeacher) {
                                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_createClassFragment);
                                    } else {
                                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_joinToClassFragment);
                                    }
                                }
                            });
                        });

                        dialog.setContentView(binding1.getRoot());
                        dialog.show();
                    });


                } else {
                    Toast.makeText(requireContext(), R.string.failure, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setTitle(getString(R.string.registration));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void dismissProgress() {
        progressDialog.dismiss();
    }

}