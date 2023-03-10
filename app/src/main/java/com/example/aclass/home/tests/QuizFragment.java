package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentQuizBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    private Test test;
    private int quizNumber = 0;
    private int rightAnswers = 0;
    private String category;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            test = getArguments().getParcelable("test");
            category = getArguments().getString("category");
            setQuestions();
        }
        binding.progress.setProgress(0);
        binding.progress.setMax(test.getQuestions().size());
        binding.tvProgress.setText("0/" + test.getQuestions().size());

        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    public void setQuestions() {

        TextView[] variants = {binding.tvVariant1, binding.tvVariant2, binding.tvVariant3, binding.tvVariant4};
        ImageView[] icons = {binding.icVariant1, binding.icVariant2, binding.icVariant3, binding.icVariant4};
        List<String> answers = test.getAnswers().get(quizNumber);
        Collections.shuffle(answers);
        AtomicBoolean isSelected = new AtomicBoolean(false);

        binding.tvQuestion.setText(test.getQuestions().get(quizNumber));
        int i = 0;
        while (i < variants.length) {

            variants[i].setText(answers.get(i));
            variants[i].setBackgroundResource(R.drawable.test_variant_unselected);
            variants[i].setTextColor(getResources().getColor(R.color.secondary_text));
            icons[i].setVisibility(View.GONE);


            int finalI = i;
            variants[i].setOnClickListener(v -> {

                if (isSelected.compareAndSet(false, true)) {

                    if (variants[finalI].getText().toString().equals(test.getRightAnswer().get(quizNumber))) {
                        variants[finalI].setBackgroundResource(R.drawable.test_variant_right);
                        variants[finalI].setTextColor(getResources().getColor(R.color.green));
                        icons[finalI].setImageResource(R.drawable.ic_right);
                        icons[finalI].setVisibility(View.VISIBLE);
                        rightAnswers++;
                    } else {
                        variants[finalI].setBackgroundResource(R.drawable.test_variant_wrong);
                        variants[finalI].setTextColor(getResources().getColor(R.color.secondary_red));
                        icons[finalI].setImageResource(R.drawable.ic_wrong);
                        icons[finalI].setVisibility(View.VISIBLE);

                        for (int j = 0; j < variants.length; j++) {
                            TextView variant = variants[j];
                            if (variant.getText().toString().equals(test.getRightAnswer().get(quizNumber))) {
                                variant.setBackgroundResource(R.drawable.test_variant_right);
                                variant.setTextColor(getResources().getColor(R.color.green));
                                icons[j].setImageResource(R.drawable.ic_right);
                                icons[j].setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    binding.progress.setProgress(binding.progress.getProgress() + 1);
                    binding.tvProgress.setText(binding.progress.getProgress() + "/" + test.getQuestions().size());
                }
            });
            i++;
        }

        if (quizNumber == test.getQuestions().size() - 1) {
            binding.btnNext.setText(getText(R.string.finish));
        }
        binding.btnNext.setOnClickListener(v -> {
            if (isSelected.compareAndSet(true, false)) {
                if (quizNumber < test.getQuestions().size() - 1) {
                    quizNumber++;
                    setQuestions();
                } else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("tests")
                            .child(category).child(String.valueOf(test.getId())).child("progress");
                    db.setValue(rightAnswers * 100 / test.getQuestions().size());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("test",test);
                    bundle.putString("category",category);
                    bundle.putInt("right",rightAnswers);
                    Navigation.findNavController(v).navigate(R.id.action_quizFragment_to_resultFragment,bundle);
                }

            }
        });
    }
}