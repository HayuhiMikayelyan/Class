package com.example.aclass.auth;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentForgotPasswordBinding;
import com.example.aclass.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class ForgotPasswordFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentForgotPasswordBinding binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);

        binding.btnReset.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.edtEmail.getEditText()).getText().toString();
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(unused -> {
                    Toast.makeText(requireContext(), R.string.check_email, LENGTH_SHORT).show();
                    Navigation.findNavController(v).popBackStack();
                });
            }
        });

        return binding.getRoot();
    }
}