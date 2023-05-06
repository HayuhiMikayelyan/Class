package com.example.aclass.home.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.auth.User;
import com.example.aclass.basic.StartActivity;
import com.example.aclass.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);

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

        binding.imgChange.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment));

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), StartActivity.class);
            startActivity(intent);
            requireActivity().finish();
            Toast.makeText(requireContext(), R.string.log_out, Toast.LENGTH_SHORT).show();
        });


        return binding.getRoot();
    }


}