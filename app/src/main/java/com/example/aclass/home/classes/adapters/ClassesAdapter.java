package com.example.aclass.home.classes.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.R;
import com.example.aclass.databinding.CodeAlertDialogBinding;
import com.example.aclass.databinding.DialogOptionsLayoutBinding;
import com.example.aclass.databinding.EditClassLayoutBinding;
import com.example.aclass.databinding.ItemClassBinding;
import com.example.aclass.home.classes.models.Class;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;


public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesViewHolder> {
    private final List<Class> classes;
    private final Context context;


    class ClassesViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        ItemClassBinding binding;

        public ClassesViewHolder(ItemClassBinding b) {
            super(b.getRoot());
            binding = b;

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                showOptionsDialog(position);
                return true;
            }
            return false;
        }
    }

    public ClassesAdapter(List<Class> classes, Context context) {
        this.classes = classes;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassesAdapter.ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassesViewHolder(ItemClassBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesAdapter.ClassesViewHolder holder, int position) {
        holder.binding.tvTitle.setText(classes.get(position).getClassName());
        holder.binding.tvSubtitle.setText(classes.get(position).getSubject());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("class", classes.get(position));
            Navigation.findNavController(v).navigate(R.id.action_classesFragment_to_lessonsFragment, bundle);
        });

        holder.binding.imgCode.setOnClickListener(v -> showCode(classes.get(position).getId(), holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    private void showCode(String id, Context context) {
        CodeAlertDialogBinding binding = CodeAlertDialogBinding.inflate(LayoutInflater.from(context));
        binding.tvCode.setText(id);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.show();
        binding.btnClose.setOnClickListener(v -> dialog.dismiss());
    }

    private void editClass(Context context, Class cl) {
        EditClassLayoutBinding binding = EditClassLayoutBinding.inflate(LayoutInflater.from(context));
        Objects.requireNonNull(binding.edtName.getEditText()).setText(cl.getClassName());

        Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v -> FirebaseFirestore.getInstance().collection("classes").document(cl.getId())
                .update("className", binding.edtName.getEditText().getText().toString()).addOnSuccessListener(unused -> dialog.dismiss()));

        dialog.show();
    }

    private void showOptionsDialog(int position) {
        DialogOptionsLayoutBinding optionsBinding = DialogOptionsLayoutBinding.inflate(LayoutInflater.from(context));

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(optionsBinding.getRoot());
        bottomSheetDialog.show();
        optionsBinding.imgDelete.setOnClickListener(v -> {
            showDialog(position);
            bottomSheetDialog.cancel();
        });

        optionsBinding.imgEdit.setOnClickListener(v -> {
            editClass(context, classes.get(position));
            bottomSheetDialog.cancel();
        });
    }

    private void showDialog(int position) {
        final boolean[] isTeacher = new boolean[1];
        FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> isTeacher[0] = Boolean.TRUE.equals(documentSnapshot.getBoolean("isTeacher")));

        int message = R.string.teacher_delete_class;
        if (!isTeacher[0]) {
            message = R.string.student_delete_class;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete)
                .setMessage(message)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteItem(position, isTeacher[0]))
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteItem(int position, boolean isTeacher) {
        if (isTeacher) {
            FirebaseFirestore.getInstance().collection("classes").document(classes.get(position).getId())
                    .delete().addOnSuccessListener(unused -> FirebaseFirestore.getInstance().collection("users")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .update("classes", FieldValue.arrayRemove(classes.get(position).getId())).addOnSuccessListener(unused1 -> {
                                classes.remove(position);
                                notifyItemRemoved(position);
                            }));
        } else {
            FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .update("classes", FieldValue.arrayRemove(classes.get(position).getId())).addOnSuccessListener(unused ->
                            FirebaseFirestore.getInstance().collection("classes").document(classes.get(position).getId()).update(
                            "membersCount", FieldValue.increment(-1)
                            , "members", FieldValue.arrayRemove(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())).addOnSuccessListener(unused12 -> {
                        classes.remove(position);
                        notifyItemRemoved(position);
                    }));
        }
    }
}
