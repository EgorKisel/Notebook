package com.geekbrains.notebook.repository;

import java.util.List;

public interface NoteSource {

    int size();

    List<NoteData> getAllNotesData();

    NoteData getNoteData(int position);
}
