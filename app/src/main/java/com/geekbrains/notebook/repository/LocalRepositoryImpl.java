package com.geekbrains.notebook.repository;

import android.content.res.Resources;

import com.geekbrains.notebook.R;

import java.util.ArrayList;
import java.util.List;

public class LocalRepositoryImpl implements NoteSource {

    private List<NoteData> dataSource;
    private Resources resources;

    public LocalRepositoryImpl(Resources resources) {
        dataSource = new ArrayList<NoteData>();
        this.resources = resources;
    }

    public LocalRepositoryImpl init() {
        String[] titles = resources.getStringArray(R.array.notebook_titles);
        String[] description = resources.getStringArray(R.array.notebook_descriptions);

        for (int i = 0; i < titles.length; i ++) {
            dataSource.add(new NoteData(titles[i], description[i]));
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<NoteData> getAllNotesData() {
        return dataSource;
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void clearNotesData() {
        dataSource.clear();
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position, newNoteData);
    }
}
