package com.example.aclass.home.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.auth.User;
import com.example.aclass.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        com.example.aclass.databinding.FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);

        FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addSnapshotListener((value, error) -> {
            if (value != null) {
                User user = value.toObject(User.class);
                if (user != null) {
                    binding.tvName.setText(user.getName());
                    binding.tvEmail.setText(user.getEmail());
                    Picasso.with(requireContext()).load(user.getIcon()).into(binding.img);
                }
            }
        });


        return binding.getRoot();
    }


}