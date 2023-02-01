package com.example.aclass.onboarding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.databinding.ItemContainerOnboardingBinding;

import java.util.List;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

    private final List<OnBoardingItem> onBoardingItems;

    static class OnBoardingViewHolder extends RecyclerView.ViewHolder {

        ItemContainerOnboardingBinding binding;

        public OnBoardingViewHolder(ItemContainerOnboardingBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public OnBoardingAdapter(List<OnBoardingItem> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(ItemContainerOnboardingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.binding.tvTitle.setText(onBoardingItems.get(position).getTitle());
        holder.binding.tvDescription.setText(onBoardingItems.get(position).getDescription());
        holder.binding.imgOnBoarding.setImageResource(onBoardingItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

}
