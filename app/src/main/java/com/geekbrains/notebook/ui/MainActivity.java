package com.geekbrains.notebook.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.geekbrains.notebook.NotebookFragment;
import com.geekbrains.notebook.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, NotebookFragment.newInstance()).commit();
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
                        .replace(R.id.container, new AboutFragment())
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