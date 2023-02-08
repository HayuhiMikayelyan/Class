package com.example.aclass.auth;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentSignInOrRegisterBinding;

public class SignInOrRegisterFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSignInOrRegisterBinding binding = FragmentSignInOrRegisterBinding.inflate(inflater, container, false);
        binding.btnRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_signInOrRegisterFragment_to_chooseFragment));

        binding.btnSignIn.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_signInOrRegisterFragment_to_signInFragment));
        return binding.getRoot();
    }
}