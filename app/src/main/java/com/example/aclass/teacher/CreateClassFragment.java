package com.example.aclass.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentCreateClassBinding;

public class CreateClassFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        com.example.aclass.databinding.FragmentCreateClassBinding binding = FragmentCreateClassBinding.inflate(inflater, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.drop_down_item,
                getResources().getStringArray(R.array.spinner_items));

        binding.filledExposed.setAdapter(adapter);


        return binding.getRoot();
    }
}