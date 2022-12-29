package com.geekbrains.notebook.repository;

public class NoteData {

    private String title;
    private String description;

    public NoteData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
