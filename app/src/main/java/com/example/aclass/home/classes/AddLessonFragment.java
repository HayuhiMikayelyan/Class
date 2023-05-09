package com.example.aclass.home.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentAddLessonBinding;
import com.example.aclass.home.classes.models.Class;
import com.example.aclass.home.classes.models.Lesson;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddLessonFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentAddLessonBinding binding = FragmentAddLessonBinding.inflate(inflater, container, false);

        if (getArguments() != null) {

            Class aClass = getArguments().getParcelable("class");

            binding.btnAddLesson.setOnClickListener(v -> {
                String title = Objects.requireNonNull(binding.edtTitle.getEditText()).getText().toString();
                String description = Objects.requireNonNull(binding.edtDescription.getEditText()).getText().toString();

                if (!title.equals("") && !description.equals("")) {
                    FirebaseFirestore.getInstance().collection("classes").document(aClass.getId()).
                            update("lessons", FieldValue.arrayUnion(new Lesson(title, description)));

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("class", aClass);
                    Navigation.findNavController(v).navigate(R.id.action_addLessonFragment_to_lessonsFragment,bundle);
                } else {
                    Toast.makeText(requireContext(), getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return binding.getRoot();
    }
}