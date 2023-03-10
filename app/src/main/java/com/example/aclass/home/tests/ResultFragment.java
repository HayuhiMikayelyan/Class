package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentResultBinding;


public class ResultFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentResultBinding binding = FragmentResultBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            Test test = getArguments().getParcelable("test");
            binding.tvRightAnswers.setText(getArguments().getInt("right") + " out of " + test.getQuestions().size() + " Questions");
            binding.tvProgress.setText(getArguments().getInt("right") * 100 / test.getQuestions().size() + "%");

            binding.btnTryAgain.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("test", test);
                bundle.putString("category", getArguments().getString("category"));
                Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_quizFragment, bundle);
            });

            binding.btnBack.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("category", getArguments().getString("category"));
                Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_testsFragment, bundle);
            });
        }


        return binding.getRoot();
    }
}