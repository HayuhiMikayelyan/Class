package com.example.aclass.auth;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentChooseBinding;

public class ChooseFragment extends Fragment{

    private FragmentChooseBinding binding;
    private boolean isSelected = false;
    private boolean isTeacher = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseBinding.inflate(inflater, container, false);

        ColorStateList selected = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary_blue_dark));
        ColorStateList unselected = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary_blue_light));
        binding.viewTeacher.setOnClickListener(view -> {
            isTeacher = true;
            isSelected = true;
            binding.viewStudent.setBackgroundTintList(unselected);
            binding.viewTeacher.setBackgroundTintList(selected);
        });

        binding.viewStudent.setOnClickListener(view -> {
            isTeacher = false;
            isSelected = true;
            binding.viewStudent.setBackgroundTintList(selected);
            binding.viewTeacher.setBackgroundTintList(unselected);
        });

        binding.btnNext.setOnClickListener(view -> {
            if (isSelected){
                Navigation.findNavController(view).navigate(R.id.action_chooseFragment_to_registerFragment);
            }
        });

        return binding.getRoot();
    }
}