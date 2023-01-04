package com.geekbrains.notebook.repository;

import java.util.List;

public interface NoteSource {

    int size();
    List<NoteData> getAllNotesData();
    NoteData getNoteData(int position);

    void clearNotesData();
    void addNoteData(NoteData noteData);
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData newNoteData);
}
