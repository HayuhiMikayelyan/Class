package com.example.aclass.home.classes;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentAddLessonBinding;
import com.example.aclass.home.classes.models.Class;
import com.example.aclass.home.classes.models.Lesson;
import com.example.aclass.home.tests.tests.Test;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class AddLessonFragment extends Fragment {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentAddLessonBinding binding = FragmentAddLessonBinding.inflate(inflater, container, false);

        if (getArguments() != null) {

            Class aClass = getArguments().getParcelable("class");

            Objects.requireNonNull(binding.edtTitle.getEditText()).setText(getArguments().getString("title"));
            Objects.requireNonNull(binding.edtDescription.getEditText()).setText(getArguments().getString("description"));

            ArrayList<Test> tests = new ArrayList<>();
            if (getArguments().getSerializable("tests") != null) {
                tests = (ArrayList<Test>) getArguments().getSerializable("tests");
                for (Test test1 : tests) {
                    TextView textView = new TextView(requireContext());
                    textView.setText(test1.getName());
                    textView.setTextSize(22);
                    textView.setTextColor(getResources().getColor(R.color.text_color));
                    textView.setTypeface(getResources().getFont(R.font.reef));

                    binding.linearLayout.addView(textView);
                }
            }

            ArrayList<Test> finalTests = tests;
            binding.btnAddTestToLesson.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("category", aClass.getSubject());
                bundle.putParcelable("class", aClass);
                bundle.putString("description", Objects.requireNonNull(binding.edtDescription.getEditText()).getText().toString());
                bundle.putString("title", Objects.requireNonNull(binding.edtTitle.getEditText()).getText().toString());
                bundle.putSerializable("tests", finalTests);
                Navigation.findNavController(v).navigate(R.id.action_addLessonFragment_to_testsFragment, bundle);
            });

            binding.btnAddLesson.setOnClickListener(v -> {
                String title = Objects.requireNonNull(binding.edtTitle.getEditText()).getText().toString();
                String description = Objects.requireNonNull(binding.edtDescription.getEditText()).getText().toString();
                ArrayList<String> ids = new ArrayList<>();
                for (Test test : finalTests) {
                    ids.add(test.getId());
                }

                if (!title.equals("") && !description.equals("")) {
                    FirebaseFirestore.getInstance().collection("classes").document(aClass.getId()).
                            update("lessons", FieldValue.arrayUnion(new Lesson(title, description,ids)));

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("class", aClass);
                    Navigation.findNavController(v).navigate(R.id.action_addLessonFragment_to_lessonsFragment, bundle);
                } else {
                    Toast.makeText(requireContext(), getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return binding.getRoot();
    }
}