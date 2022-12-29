package com.geekbrains.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notebook.repository.LocalRepositoryImpl;
import com.geekbrains.notebook.ui.NoteAdapter;
import com.geekbrains.notebook.ui.OnItemClickListener;

public class NotebookFragment extends Fragment implements OnItemClickListener {

    NoteAdapter notesAdapter;

    public static NotebookFragment newInstance() {
        NotebookFragment fragment = new NotebookFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
    }

    private void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
//        recyclerView.addItemDecoration(itemDecoration);
    }

    private void initAdapter() {
        notesAdapter = new NoteAdapter();
        LocalRepositoryImpl localRepositoryImpl = new LocalRepositoryImpl(requireContext().getResources());
        notesAdapter.setData(localRepositoryImpl.init());
        notesAdapter.setOnItemClickListener(NotebookFragment.this);
    }

    String[] getData() {
        String[] data = getResources().getStringArray(R.array.notebook_titles);
        return data;
    }

    @Override
    public void onItemClick(int position) {
        String[] data = getData();
        Toast.makeText(requireContext(), " Нажали на " + data[position], Toast.LENGTH_SHORT).show();
    }
}
