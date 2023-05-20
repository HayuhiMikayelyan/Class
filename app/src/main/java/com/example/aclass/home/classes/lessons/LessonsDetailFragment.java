package com.example.aclass.home.classes.lessons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentLessonsDetailBinding;
import com.example.aclass.databinding.TestCardBinding;
import com.example.aclass.home.classes.models.Lesson;
import com.example.aclass.home.tests.tests.Test;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LessonsDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLessonsDetailBinding binding = FragmentLessonsDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            Lesson lesson = getArguments().getParcelable("lesson");
            String subject = getArguments().getString("subject");

            binding.tvLessonTitle.setText(lesson.getTitle());
            binding.tvDescription.setText(lesson.getDescription());

            FirebaseDatabase.getInstance().getReference("tests").child(subject).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Test test = child.getValue(Test.class);
                        assert test != null;
                        if (lesson.getTests().contains(test.getId())) {
                            TestCardBinding cardBinding = TestCardBinding.inflate(inflater, binding.linearLayout, false);
                            cardBinding.tvTest.setText(test.getName());

                            cardBinding.getRoot().setOnClickListener(v -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("test",test);
                                bundle.putString("subject",subject);
                                Navigation.findNavController(v).navigate(R.id.action_lessonsDetailFragment_to_quizFragment,bundle);
                            });
                            binding.linearLayout.addView(cardBinding.getRoot());
                        }
                    }
                }
            });
        }

        return binding.getRoot();
    }
}