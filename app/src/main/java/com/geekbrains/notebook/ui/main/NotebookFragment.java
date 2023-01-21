package com.geekbrains.notebook.ui.main;

import static com.geekbrains.notebook.repository.LocalSharedPreferencesRepositoryImpl.KEY_SP_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notebook.R;
import com.geekbrains.notebook.publisher.Observer;
import com.geekbrains.notebook.repository.LocalRepositoryImpl;
import com.geekbrains.notebook.repository.LocalSharedPreferencesRepositoryImpl;
import com.geekbrains.notebook.repository.NoteData;
import com.geekbrains.notebook.repository.NoteSource;
import com.geekbrains.notebook.ui.MainActivity;
import com.geekbrains.notebook.ui.editor.CardFragment;

import java.util.Calendar;

public class NotebookFragment extends Fragment implements OnItemClickListener {

    NoteAdapter notesAdapter;
    NoteSource data;
    RecyclerView recyclerView;

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
        setupSource();
        initRecycler(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);
    }

    void setupSource() {
        switch (getCurrentSource()) {
            case SOURCE_ARRAY:
                data = new LocalRepositoryImpl(requireContext().getResources()).init();
                initAdapter();
                break;

            case SOURCE_SP:
                data = new LocalSharedPreferencesRepositoryImpl(requireContext().getSharedPreferences(KEY_SP_2, Context.MODE_PRIVATE)).init();
                initAdapter();
                break;

            case SOURCE_GF:
                //data = new RemoteFireStoreRepositoryImpl(requireContext().getResources()).init();
                initAdapter();
                break;
        }
    }

    private void initRadioGroup(View view) {
        view.findViewById(R.id.sourceArrays).setOnClickListener(listener);
        view.findViewById(R.id.sourceSP).setOnClickListener(listener);
        view.findViewById(R.id.sourceGF).setOnClickListener(listener);

        switch (getCurrentSource()) {
            case SOURCE_ARRAY:
                ((RadioButton) view.findViewById(R.id.sourceArrays)).setChecked(true);
                break;

            case SOURCE_SP:
                ((RadioButton) view.findViewById(R.id.sourceSP)).setChecked(true);
                break;

            case SOURCE_GF:
                ((RadioButton) view.findViewById(R.id.sourceGF)).setChecked(true);
                break;
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sourceArrays:
                    setCurrentSource(SOURCE_ARRAY);
                    break;

                case R.id.sourceSP:
                    setCurrentSource(SOURCE_SP);
                    break;

                case R.id.sourceGF:
                    setCurrentSource(SOURCE_GF);
                    break;
            }
            setupSource();
        }
    };

    static String KEY_SP_S1 = "key_sp_s1";
    static String KEY_SP_S1_CELL_C1 = "key_sp_s1_cell_c1";
    static final int SOURCE_ARRAY = 1;
    static final int SOURCE_SP = 2;
    static final int SOURCE_GF = 3;

    void setCurrentSource(int currentSource) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SP_S1_CELL_C1, currentSource);
        editor.apply();
    }

    int getCurrentSource() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SP_S1_CELL_C1, SOURCE_ARRAY);
    }

    private void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notesAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(500);
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void initAdapter() {
        if (notesAdapter == null)
        notesAdapter = new NoteAdapter(this);
        notesAdapter.setData(data);
        notesAdapter.setOnItemClickListener(NotebookFragment.this);
    }

    String[] getData() {
        String[] data = getResources().getStringArray(R.array.notebook_titles);
        return data;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuPosition = notesAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case (R.id.action_about): {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, AboutFragment.newInstance()).addToBackStack("").commit();
                return true;
            }
            case (R.id.action_exit): {
                MyDialogFragmentCustom myDialogFragmentCustom = new MyDialogFragmentCustom();
                myDialogFragmentCustom.show(requireActivity().getSupportFragmentManager(), "sdfgv");
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
                data.addNoteData(new NoteData(getString(R.string.title_of_the_new_note) + data.size(), getString(R.string.description_of_the_new_note) + data.size(), Calendar.getInstance().getTime()));
                notesAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.smoothScrollToPosition(data.size() - 1);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = notesAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case (R.id.action_update): {
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.updateNoteData(menuPosition, noteData);
                        notesAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(CardFragment.newInstance(data.getNoteData(menuPosition)), true);
                return true;
            }
            case (R.id.action_delete): {
                data.deleteNoteData(menuPosition);
                notesAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}
