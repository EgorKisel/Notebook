package com.geekbrains.notebook.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.geekbrains.notebook.Notebook;
import com.geekbrains.notebook.NotebookDescriptionsFragment;
import com.geekbrains.notebook.NotebookTitlesFragment;
import com.geekbrains.notebook.R;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_about): {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.notebook_titles, new AboutFragment())
                        .addToBackStack("").commit();
                return true;
            }
            case (R.id.action_exit): {
                finish();
                return true;
            }
            case (R.id.action_sort): {
                Toast.makeText(this, "Sorting in progress", Toast.LENGTH_LONG).show();
                // TODO finish sorting
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Пришлось перенести наш костыль в onResume
     * так как не onBackPressed() вызывать в onCreate - черевато
     **/
    @Override
    protected void onResume() {
        super.onResume();
        // ищем фрагмент, который сидит в контейнере R.id.notebook_titles
        Fragment backStackFragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.notebook_titles);
        // если такой есть, и он является NotebookDescriptionsFragment
        if (backStackFragment != null && backStackFragment instanceof NotebookDescriptionsFragment) {
            //то сэмулируем нажатие кнопки Назад
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (this.getSupportFragmentManager().getBackStackEntryCount() == 0){
            MyDialogFragmentCustom myDialogFragmentCustom = new MyDialogFragmentCustom();
            myDialogFragmentCustom.show(this.getSupportFragmentManager(),"sdfgv");
        } else {
            super.onBackPressed();
        }
    }
}