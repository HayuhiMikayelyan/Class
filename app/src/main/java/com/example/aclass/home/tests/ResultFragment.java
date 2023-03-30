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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResultFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentResultBinding binding = FragmentResultBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            Test test = getArguments().getParcelable("test");
            String category = getArguments().getString("category");
            int progress = getArguments().getInt("right") * 100 / test.getQuestions().size();

            binding.tvRightAnswers.setText(getArguments().getInt("right") + " out of " + test.getQuestions().size() + " Questions");
            binding.tvProgress.setText(progress + "%");

            binding.btnTryAgain.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("test", test);
                bundle.putString("category", getArguments().getString("category"));
                Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_quizFragment, bundle);
            });

            binding.btnBack.setOnClickListener(v -> {

                FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addSnapshotListener((value, error) -> {
                    List<Map<String, Long>> list = (List<Map<String, Long>>) value.get("tests");

                    boolean isChanged = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).containsKey(test.getId())) {
                            long oldProgress = list.get(i).get(test.getId());
                            Map<String, Long> map = new HashMap<>();
                            map.put(test.getId(), Math.max(oldProgress, progress));
                            list.set(i, map);
                            isChanged = true;
                        }
                    }
                    if (!isChanged) {
                        Map<String, Long> map = new HashMap<>();
                        map.put(test.getId(), (long) progress);
                        list.add(map);
                    }
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).update("tests", list).addOnSuccessListener(unused -> {
                    });
                });

                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_testsFragment, bundle);
            });
        }


        return binding.getRoot();
    }
}