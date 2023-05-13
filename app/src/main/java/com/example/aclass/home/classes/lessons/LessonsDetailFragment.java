package com.example.aclass.home.classes.lessons;

import  android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentLessonsDetailBinding;
import com.example.aclass.home.classes.models.Lesson;
import com.example.aclass.home.tests.tests.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LessonsDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLessonsDetailBinding binding = FragmentLessonsDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            Lesson lesson = getArguments().getParcelable("lesson");
            binding.tvLessonTitle.setText(lesson.getTitle());
            binding.tvLessonDescription.setText(lesson.getDescription());

            ArrayList<Test> tests = new ArrayList<>();
            String subject = getArguments().getString("subject");

            FirebaseDatabase.getInstance().getReference().child("tests").child(subject).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Test test = child.getValue(Test.class);
                        if (test != null && lesson.getTests().contains(test.getId())) {
                            TextView textView = new TextView(requireContext());
                            textView.setText(test.getName());
                            textView.setTextSize(22);
                            textView.setTextColor(getResources().getColor(R.color.text_color));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                textView.setTypeface(getResources().getFont(R.font.reef));
                            }
                            binding.linearLayout.addView(textView);

                            textView.setOnClickListener(v -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("test", test);
                                bundle.putString("subject", subject);
                                Navigation.findNavController(v).navigate(R.id.action_lessonsDetailFragment_to_quizFragment, bundle);
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                }
            });

        }
        return binding.getRoot();
    }
}