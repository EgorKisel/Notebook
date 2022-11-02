package com.geekbrains.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

    /** Пришлось перенести наш костыль в onResume
     * так как не onBackPressed() вызывать в onCreate - черевато
     **/
    @Override
    protected void onResume() {
        super.onResume();
        // ищем фрагмент, который сидит в контейнере R.id.notebook_titles
        Fragment backStackFragment = (Fragment)getSupportFragmentManager()
                .findFragmentById(R.id.notebook_titles);
        // если такой есть, и он является CoatOfArmsFragment
        if(backStackFragment!=null&&backStackFragment instanceof NotebookDescriptionsFragment){
            //то сэмулируем нажатие кнопки Назад
            onBackPressed();
        }
    }
}