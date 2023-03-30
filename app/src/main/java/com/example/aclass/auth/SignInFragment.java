package com.example.aclass.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.basic.MainActivity;
import com.example.aclass.databinding.FragmentSignInBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);

        progressDialog = new ProgressDialog(requireContext());
        auth = FirebaseAuth.getInstance();

        binding.btnSignIn.setOnClickListener(this::PerformSignIn);

        return binding.getRoot();
    }

    private void PerformSignIn(View view) {
        String email = Objects.requireNonNull(binding.edtEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(binding.edtPassword.getEditText()).getText().toString();

        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern)) {
            binding.edtEmail.setError(getString(R.string.wrong_email));
        } else if (password.isEmpty() || password.length() < 6) {
            binding.edtEmail.setError(null);
            binding.edtPassword.setError(getString(R.string.wrong_password));
        } else {
            binding.edtEmail.setError(null);
            binding.edtPassword.setError(null);
            progressDialog.setMessage(getString(R.string.wait));
            progressDialog.setTitle(getString(R.string.sign_in));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), R.string.wrong_email_password, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}