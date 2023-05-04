package com.example.aclass.home.tests.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentQuestionsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class AddQuestionsFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentQuestionsBinding binding = FragmentQuestionsBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            AtomicInteger currentQuestion = new AtomicInteger(1);
            Test test = getArguments().getParcelable("test");
            int questionCount = getArguments().getInt("count");

            binding.tvQuestion.setText(getString(R.string.question) + " " + currentQuestion);

            binding.btnNext.setOnClickListener(v -> {

                ArrayList<String> variants = new ArrayList<>();

                String question = Objects.requireNonNull(binding.edtQuestion.getEditText()).getText().toString();
                variants.add(Objects.requireNonNull(binding.edtVariant1.getEditText()).getText().toString());
                variants.add(Objects.requireNonNull(binding.edtVariant2.getEditText()).getText().toString());
                variants.add(Objects.requireNonNull(binding.edtVariant3.getEditText()).getText().toString());
                variants.add(Objects.requireNonNull(binding.edtVariant4.getEditText()).getText().toString());

                if (!question.isEmpty() && !variants.get(0).isEmpty() && !variants.get(1).isEmpty() &&
                        !variants.get(2).isEmpty() && !variants.get(3).isEmpty() && binding.right.getCheckedRadioButtonId() != -1) {

                    String rightAnswer = variants.get((int) binding.right.getCheckedRadioButtonId() - 2);
                    test.addQuestion(question);
                    test.addAnswers(variants);
                    test.addRightAnswer(rightAnswer);

                    if (currentQuestion.intValue() < questionCount) {
                        binding.edtVariant1.getEditText().setText("");
                        binding.edtVariant2.getEditText().setText("");
                        binding.edtVariant3.getEditText().setText("");
                        binding.edtVariant4.getEditText().setText("");
                        binding.edtQuestion.getEditText().setText("");
                        binding.right.clearCheck();
                        currentQuestion.getAndIncrement();
                        binding.tvQuestion.setText(getString(R.string.question) + " " + currentQuestion);
                    } else {
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("tests")
                                .child(getArguments().getString("category")).child(test.getId());

                        database.setValue(test).addOnSuccessListener(unused -> {
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("categories").document(getArguments().getString("category")).update("count", FieldValue.increment(1));
                            Bundle bundle = new Bundle();
                            bundle.putString("category", getArguments().getString("category"));
                            Navigation.findNavController(v).navigate(R.id.action_questionsFragment_to_testsFragment, bundle);
                        });
                    }


                } else {
                    Toast.makeText(requireContext(), getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                }

            });
        }


        return binding.getRoot();
    }
}