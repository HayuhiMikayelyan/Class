package com.example.aclass.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentJoinToClassBinding;

public class JoinToClassFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentJoinToClassBinding binding = FragmentJoinToClassBinding.inflate(inflater,container,false);

        return inflater.inflate(R.layout.fragment_join_to_class, container, false);
    }
}