package com.example.aclass.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aclass.R;
import com.example.aclass.databinding.FragmentHomeBinding;
import com.example.aclass.home.classes.ClassesFragment;
import com.example.aclass.home.profile.ProfileFragment;
import com.example.aclass.home.tests.TestsFragment;


public class HomeFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);

        ClassesFragment classesFragment = new ClassesFragment();
        TestsFragment testsFragment = new TestsFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        loadFragment(classesFragment);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.classesFragment:
                    loadFragment(classesFragment);
                    break;
                case R.id.testsFragment:
                    loadFragment(testsFragment);
                    break;
                case R.id.profileFragment:
                    loadFragment(profileFragment);
                    break;
            }
            return true;
        });

        return binding.getRoot();
    }

    private void loadFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}