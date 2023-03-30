package com.example.aclass.home.tests;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.R;
import com.example.aclass.auth.User;
import com.example.aclass.databinding.ItemTestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestsViewHolder> {
    private final List<Test> tests;
    private final String category;

    static class TestsViewHolder extends RecyclerView.ViewHolder {

        ItemTestBinding binding;

        public TestsViewHolder(ItemTestBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public TestsAdapter(List<Test> tests, String category) {
        this.tests = tests;
        this.category = category;
    }

    @NonNull
    @Override
    public TestsAdapter.TestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestsAdapter.TestsViewHolder(ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TestsAdapter.TestsViewHolder holder, int position) {
        holder.binding.tvTitle.setText(tests.get(position).getName());
        holder.binding.tvSubtitle.setText(tests.get(position).getQuestions().size() + " " + holder.itemView.getContext().getText(R.string.questions));
        holder.binding.tvProgress.setText("0%");
        holder.binding.progressBar.setProgress(0);
        FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addSnapshotListener((value, error) -> {
            if (value != null) {
                User user = value.toObject(User.class);
                if (user != null && user.getTests() != null) {
                    for (Map<String, Integer> map : user.getTests()) {
                        if (map.get(tests.get(position).getId()) != null) {
                            holder.binding.progressBar.setProgress(map.get(tests.get(position).getId()));
                            holder.binding.tvProgress.setText(map.get(tests.get(position).getId()) + "%");
                        }
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("test", tests.get(position));
            bundle.putString("category", category);
            Navigation.findNavController(v).navigate(R.id.action_testsFragment_to_quizFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }


}
