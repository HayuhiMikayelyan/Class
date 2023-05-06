package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.CodeAlertDialogBinding;
import com.example.aclass.databinding.FragmentClassesDetailBinding;

public class ClassesDetailFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentClassesDetailBinding binding = FragmentClassesDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            binding.tvClassName.setText(getArguments().getString("className"));
            binding.tvMembers.setText(getArguments().getInt("members") + getString(R.string.members));

            binding.tvLessons.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("className", binding.tvClassName.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_classesDetailFragment_to_lessonsFragment, bundle);
            });

            binding.tvMembers.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("className", binding.tvClassName.getText().toString());
                bundle.putString("id",getArguments().getString("id"));
                Navigation.findNavController(v).navigate(R.id.action_classesDetailFragment_to_classMembersFragment,bundle);
            });
        }


        return binding.getRoot();
    }


}