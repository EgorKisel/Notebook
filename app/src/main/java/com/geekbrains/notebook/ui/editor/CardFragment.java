package com.geekbrains.notebook.ui.editor;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.notebook.R;
import com.geekbrains.notebook.repository.NoteData;
import com.geekbrains.notebook.ui.MainActivity;

import java.util.Calendar;

public class CardFragment extends Fragment {

    NoteData noteData;
    private DatePicker datePicker;
    Calendar calendar;

    public static CardFragment newInstance(NoteData noteData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable("noteData", noteData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable("noteData");
            ((EditText) view.findViewById(R.id.inputTitle)).setText(noteData.getTitle());
            ((EditText) view.findViewById(R.id.inputDescription)).setText(noteData.getDescription());

            calendar = Calendar.getInstance();
            calendar.setTime(noteData.getDate());
            ((DatePicker) view.findViewById(R.id.inputDate)).init(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((DatePicker) view.findViewById(R.id.inputDate)).setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                    }
                });
            }

            view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View it) {
                    noteData.setTitle(((EditText) view.findViewById(R.id.inputTitle)).getText().toString());
                    noteData.setDescription(((EditText) view.findViewById(R.id.inputDescription)).getText().toString());

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        DatePicker datePicker = ((DatePicker) view.findViewById(R.id.inputDate));
                        calendar.set(Calendar.YEAR, datePicker.getYear());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    }

                    noteData.setDate(calendar.getTime());

                    ((MainActivity) requireActivity()).getPublisher().sendMessage(noteData);
                    ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();
                }
            });
        }
    }
}