package com.geekbrains.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotebookDescriptionsFragment extends Fragment {

    public static final String ARG_NOTEBOOK = "notebook";

    private Notebook notebook;

    public static NotebookDescriptionsFragment newInstance(Notebook notebook) {
        NotebookDescriptionsFragment fragment = new NotebookDescriptionsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTEBOOK, notebook);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_descriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notebook = getArguments().getParcelable(ARG_NOTEBOOK);
        setHasOptionsMenu(true);
        String[] notebookDescription = getResources().getStringArray(R.array.notebook_descriptions);
        TextView textView = new TextView(getContext());
        textView.setTextSize(30f);
        textView.setText(notebookDescription[notebook.getIndex()]);
        ((LinearLayout) view).addView(textView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notebook_description_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_back): {
                requireActivity().getSupportFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
