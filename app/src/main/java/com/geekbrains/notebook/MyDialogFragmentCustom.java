package com.geekbrains.notebook;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragmentCustom extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_close_app)
                .setMessage(R.string.dialog_exit_app)
                .setPositiveButton(R.string.dialog_yes_exit_app, (dialog, which) -> {
                    requireActivity().finish();
                    showToast(R.string.dialog_exit);
                })
                .setNegativeButton(R.string.dialog_no_exit_app, (dialog, which) -> {
                    showToast(R.string.dialog_no_exit_app);
                })
                .show();
    }

    void showToast(Integer message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
