package com.geekbrains.notebook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotebookTitlesFragment extends Fragment {

    public static NotebookTitlesFragment newInstance() {
        NotebookTitlesFragment fragment = new NotebookTitlesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_titles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] notebookTitles = getResources().getStringArray(R.array.notebook_titles);
        for (int i = 0; i < notebookTitles.length; i++) {
            String title = notebookTitles[i];
            TextView textView = new TextView(getContext());
            textView.setTextSize(30f);
            textView.setText(title);
            ((LinearLayout) view).addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notebook notebook = new Notebook(finalI);
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        NotebookDescriptionsFragment notebookDescriptionsFragment = NotebookDescriptionsFragment.newInstance(notebook);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.notebook_descriptions, notebookDescriptionsFragment).commit();
                    } else {
                        NotebookDescriptionsFragment notebookDescriptionsFragment = NotebookDescriptionsFragment.newInstance(notebook);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.notebook_titles, notebookDescriptionsFragment)
                                .addToBackStack("").commit();
                    }
                }
            });
        }
    }
}
