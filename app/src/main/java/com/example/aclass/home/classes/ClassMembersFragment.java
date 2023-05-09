package com.example.aclass.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aclass.auth.User;
import com.example.aclass.databinding.FragmentClassMembersBinding;
import com.example.aclass.home.classes.adapters.MembersAdapter;
import com.example.aclass.home.classes.models.Class;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ClassMembersFragment extends Fragment {

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentClassMembersBinding binding = FragmentClassMembersBinding.inflate(inflater, container, false);


        if (getArguments() != null) {
            ArrayList<User> members = new ArrayList<>();
            MembersAdapter adapter = new MembersAdapter(members, requireContext());

            binding.recycler.setHasFixedSize(true);
            binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recycler.setAdapter(adapter);

            Class aClass = getArguments().getParcelable("class");
            binding.tvClassName.setText(aClass.getClassName());

            DocumentReference document = FirebaseFirestore.getInstance().collection("classes").document(aClass.getId());
            document.addSnapshotListener((value, error) -> {
                if (value != null) {
                    Class cl = value.toObject(Class.class);

                    if (cl != null) {
                        for (String memberId : cl.getMembers()) {
                            FirebaseFirestore.getInstance().collection("users").document(memberId).addSnapshotListener((value1, error1) -> {
                                if (value1 != null) {
                                    members.add(value1.toObject(User.class));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                }
            });
        }
        return binding.getRoot();
    }
}