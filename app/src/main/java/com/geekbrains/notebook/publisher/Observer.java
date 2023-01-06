package com.geekbrains.notebook.publisher;

import com.geekbrains.notebook.repository.NoteData;

public interface Observer {
    void receiveMessage(NoteData noteData);
}
