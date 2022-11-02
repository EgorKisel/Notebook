package com.geekbrains.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NotebookTitlesFragment notebookTitlesFragment = NotebookTitlesFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.notebook_titles, notebookTitlesFragment).commit();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Notebook notebook = new Notebook(0);
                NotebookDescriptionsFragment notebookDescriptionsFragment = NotebookDescriptionsFragment.newInstance(notebook);
                getSupportFragmentManager().beginTransaction().replace(R.id.notebook_descriptions, notebookDescriptionsFragment).commit();
            }
        }
    }
}