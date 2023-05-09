package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aclass.R;
import com.example.aclass.auth.User;
import com.example.aclass.databinding.FragmentLessonsBinding;
import com.example.aclass.home.classes.adapters.LessonAdapter;
import com.example.aclass.home.classes.models.Class;
import com.example.aclass.home.classes.models.Lesson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class LessonsFragment extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLessonsBinding binding = FragmentLessonsBinding.inflate(inflater, container, false);
        binding.btnAddLesson.setVisibility(View.GONE);
        binding.tvNoLessons.setVisibility(View.GONE);
        binding.btnAddLessonF.setVisibility(View.GONE);

        if (getArguments() != null) {
            Class aClass = getArguments().getParcelable("class");
            binding.tvMembers.setText(aClass.getMembersCount() + getString(R.string.members));
            binding.tvClassName.setText(aClass.getClassName());

            FirebaseFirestore.getInstance().collection("classes").document(aClass.getId()).get().addOnSuccessListener(documentSnapshot -> {
                ArrayList<Lesson> lessons = Objects.requireNonNull(documentSnapshot.toObject(Class.class)).getLessons();
                if (lessons.size() == 0) {
                    FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addSnapshotListener((value, error) -> {
                        if (value != null) {
                            if (Objects.requireNonNull(value.toObject(User.class)).getIsTeacher()) {
                                binding.btnAddLesson.setVisibility(View.VISIBLE);
                                binding.tvNoLessons.setVisibility(View.VISIBLE);

                            }
                        }
                    });
                } else {
                    LessonAdapter adapter = new LessonAdapter(lessons);
                    binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recycler.setHasFixedSize(true);
                    binding.recycler.setAdapter(adapter);
                    binding.btnAddLessonF.setVisibility(View.VISIBLE);
                }
            });

            binding.btnAddLesson.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("class", aClass);
                Navigation.findNavController(v).navigate(R.id.action_lessonsFragment_to_addLessonFragment, bundle);
            });

            binding.btnAddLessonF.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("class", aClass);
                Navigation.findNavController(v).navigate(R.id.action_lessonsFragment_to_addLessonFragment, bundle);
            });

            binding.tvMembers.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("class", aClass);
                Navigation.findNavController(v).navigate(R.id.action_lessonsFragment_to_classMembersFragment, bundle);

            });
        }

        return binding.getRoot();
    }
}