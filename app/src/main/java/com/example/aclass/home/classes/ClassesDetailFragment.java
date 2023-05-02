package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.CodeAlertDialogBinding;
import com.example.aclass.databinding.FragmentClassesDetailBinding;

public class ClassesDetailFragment extends Fragment {

    private CodeAlertDialogBinding alertBinding;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentClassesDetailBinding binding = FragmentClassesDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            binding.tvClassName.setText(getArguments().getString("className"));
            binding.tvMembers.setText(getArguments().getInt("members") + getString(R.string.members));
        }
        return binding.getRoot();
    }


}