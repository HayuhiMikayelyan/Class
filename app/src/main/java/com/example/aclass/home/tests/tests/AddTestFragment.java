package com.example.aclass.home.tests.tests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentAddTestBinding;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AddTestFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentAddTestBinding binding = FragmentAddTestBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(v -> {
            try {
                if (getArguments() != null) {
                    String testName = Objects.requireNonNull(binding.edtName.getEditText()).getText().toString();
                    int questionCount = Integer.parseInt(Objects.requireNonNull(binding.edtQuestionCount.getEditText()).getText().toString());
                    String id = UUID.randomUUID().toString().replace("-", "");

                    if (!testName.equals("") && questionCount > 0) {

                        Test test = new Test(id, testName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("test", test);
                        bundle.putInt("count", questionCount);
                        bundle.putString("category", getArguments().getString("category"));
                        Navigation.findNavController(v).navigate(R.id.action_addTestFragment_to_questionsFragment, bundle);

                    } else {
                        Toast.makeText(requireContext(), getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                        binding.edtQuestionCount.getEditText().setText("");
                        binding.edtName.getEditText().setText("");
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}