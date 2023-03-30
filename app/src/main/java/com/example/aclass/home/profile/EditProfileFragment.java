package com.example.aclass.home.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aclass.R;
import com.example.aclass.auth.User;
import com.example.aclass.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private Uri uri = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addSnapshotListener((value, error) -> {
            if (value != null) {
                User user = value.toObject(User.class);
                if (user != null) {
                    binding.edtNameT.setText(user.getName());
                    binding.edtEmailT.setText(user.getEmail());
                    Picasso.with(requireContext()).load(user.getIcon()).into(binding.img);
                }
            }
        });

        binding.tvChangeIcon.setOnClickListener(v -> {
            changeImage();
        });

        binding.btnSave.setOnClickListener(v -> {
            Map<String, Object> user = new HashMap<>();
            user.put("name", Objects.requireNonNull(binding.edtName.getEditText()).getText().toString());
            user.put("email", Objects.requireNonNull(binding.edtEmail.getEditText()).getText().toString());
            if (uri != null) {
                FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString()).putFile(uri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                            user.put("icon", uri.toString());
                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(user);
                        });
                    }
                });
            } else {
                FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(user);
            }
            Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_profileFragment);
        });

        return binding.getRoot();
    }

    private void changeImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
            binding.img.setImageURI(uri);
        }
    }
}