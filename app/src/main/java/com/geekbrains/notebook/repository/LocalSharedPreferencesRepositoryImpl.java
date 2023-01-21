package com.geekbrains.notebook.repository;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalSharedPreferencesRepositoryImpl implements NoteSource {

    static final String KEY_CELL_1 = "cell_2";
    public static final String KEY_SP_2 = "key_sp_2";

    private List<NoteData> dataSource;
    private SharedPreferences sharedPreferences;

    public LocalSharedPreferencesRepositoryImpl(SharedPreferences sharedPreferences) {
        dataSource = new ArrayList<NoteData>();
        this.sharedPreferences = sharedPreferences;
    }

    public LocalSharedPreferencesRepositoryImpl init() {
        String savedNote = sharedPreferences.getString(KEY_CELL_1, null);
        if (savedNote != null) {
            Type type = new TypeToken<ArrayList<NoteData>>(){}.getType();
            dataSource = (new GsonBuilder().create().fromJson(savedNote, type));
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position, newNoteData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
}
