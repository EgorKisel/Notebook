package com.geekbrains.notebook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotebookTitlesFragment extends Fragment {

    private Notebook currentNote;
    public static final String CURRENT_NOTE = "note_current";

    public static NotebookTitlesFragment newInstance() {
        NotebookTitlesFragment fragment = new NotebookTitlesFragment();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, currentNote);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_titles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Notebook(0);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLand(currentNote);
        }

        init((LinearLayout) view);
    }

    private void init(LinearLayout view) {
        String[] notebookTitles = getResources().getStringArray(R.array.notebook_titles);
        for (int i = 0; i < notebookTitles.length; i++) {
            String title = notebookTitles[i];
            TextView textView = new TextView(getContext());
            textView.setTextSize(30f);
            textView.setText(title);
            view.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notebook notebook = new Notebook(finalI);
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        showLand(notebook);
                    } else {
                        showPort(notebook);
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(requireContext(), view, Gravity.CENTER);
                    requireActivity().getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case (R.id.action_popup_del): {

                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }
    }

    private void showPort(Notebook notebook) {
        NotebookDescriptionsFragment notebookDescriptionsFragment = NotebookDescriptionsFragment.newInstance(notebook);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.notebook_titles, notebookDescriptionsFragment)
                .addToBackStack("").commit();
    }

    private void showLand(Notebook notebook) {
        NotebookDescriptionsFragment notebookDescriptionsFragment = NotebookDescriptionsFragment.newInstance(notebook);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.notebook_descriptions, notebookDescriptionsFragment).commit();
    }
}
