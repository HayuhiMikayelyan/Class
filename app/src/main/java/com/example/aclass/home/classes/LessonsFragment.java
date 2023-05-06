package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.databinding.FragmentLessonsBinding;

public class LessonsFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLessonsBinding binding = FragmentLessonsBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            binding.tvClassName.setText(getArguments().getString("className"));
        }

        return binding.getRoot();
    }
}