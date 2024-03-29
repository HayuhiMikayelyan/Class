package com.example.aclass.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aclass.R;
import com.example.aclass.basic.MainActivity;
import com.example.aclass.databinding.FragmentOnBoardingBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class OnBoardingFragment extends Fragment {
    private OnBoardingAdapter onBoardingAdapter;
    private FragmentOnBoardingBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false);
        FirebaseApp.initializeApp(requireContext());

        sharedPreferences = requireContext().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("FirstTime", true);
        if (!isFirstTime) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Navigation.findNavController(container).navigate(R.id.action_onBoardingFragment_to_signInOrRegisterFragment);
            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        }
        setupOnBoardingItems();
        binding.onBoardingViewPager.setAdapter(onBoardingAdapter);
        setupOnBoardingIndicators();
        setupCurrentOnBoardingIndicator(0);
        binding.onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupCurrentOnBoardingIndicator(position);
            }
        });

        binding.btnNext.setOnClickListener(view -> {
            if (binding.onBoardingViewPager.getCurrentItem() < onBoardingAdapter.getItemCount() - 1) {
                binding.onBoardingViewPager.setCurrentItem(binding.onBoardingViewPager.getCurrentItem() + 1);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("FirstTime", false);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.action_onBoardingFragment_to_signInOrRegisterFragment);
            }
        });

        return binding.getRoot();
    }

    private void setupOnBoardingItems() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem item1 = new OnBoardingItem(
                R.drawable.on_boarding_1,
                getString(R.string.on_boarding_title_1),
                getString(R.string.on_boarding_description_1));

        OnBoardingItem item2 = new OnBoardingItem(
                R.drawable.on_boarding_2,
                getString(R.string.on_boarding_title_2),
                getString(R.string.on_boarding_description_2));

        OnBoardingItem item3 = new OnBoardingItem(
                R.drawable.on_boarding_3,
                getString(R.string.on_boarding_title_3),
                getString(R.string.on_boarding_description_3));

        onBoardingItems.add(item1);
        onBoardingItems.add(item2);
        onBoardingItems.add(item3);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }

    private void setupOnBoardingIndicators() {
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutOnBoardingIndicators.addView(indicators[i]);
        }
    }

    private void setupCurrentOnBoardingIndicator(int index) {
        int childCount = binding.layoutOnBoardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutOnBoardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onBoardingAdapter.getItemCount() - 1) {
            binding.btnNext.setText(R.string.start);
        } else {
            binding.btnNext.setText(R.string.next);
        }
    }
}