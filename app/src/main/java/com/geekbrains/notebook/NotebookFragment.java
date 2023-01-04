package com.geekbrains.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notebook.repository.LocalRepositoryImpl;
import com.geekbrains.notebook.repository.NoteData;
import com.geekbrains.notebook.repository.NoteSource;
import com.geekbrains.notebook.ui.AboutFragment;
import com.geekbrains.notebook.ui.MyDialogFragmentCustom;
import com.geekbrains.notebook.ui.NoteAdapter;
import com.geekbrains.notebook.ui.OnItemClickListener;

public class NotebookFragment extends Fragment implements OnItemClickListener {

    NoteAdapter notesAdapter;
    NoteSource data;

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
        setHasOptionsMenu(true);
    }

    private void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void initAdapter() {
        notesAdapter = new NoteAdapter();
        data = new LocalRepositoryImpl(requireContext().getResources()).init();
        notesAdapter.setData(data);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_about): {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AboutFragment.newInstance())
                        .addToBackStack("").commit();
                return true;
            }
            case (R.id.action_exit): {
                MyDialogFragmentCustom myDialogFragmentCustom = new MyDialogFragmentCustom();
                myDialogFragmentCustom.show(requireActivity().getSupportFragmentManager(),"sdfgv");
                return true;
            }
            case (R.id.action_sort): {
                Toast.makeText(requireContext(), "Sorting in progress", Toast.LENGTH_LONG).show();
                // TODO finish sorting
                return true;
            }
            case (R.id.action_clear_all): {
                data.clearNotesData();
                notesAdapter.notifyDataSetChanged();
                return true;
            }
            case (R.id.action_add): {
                data.addNoteData(new NoteData(getString(R.string.title_of_the_new_note) + data.size(), getString(R.string.description_of_the_new_note) + data.size()));
                notesAdapter.notifyItemInserted(data.size() - 1);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
