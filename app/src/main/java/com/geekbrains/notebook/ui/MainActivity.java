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
    public void onBackPressed() {
        if (this.getSupportFragmentManager().getBackStackEntryCount() == 0){
            MyDialogFragmentCustom myDialogFragmentCustom = new MyDialogFragmentCustom();
            myDialogFragmentCustom.show(this.getSupportFragmentManager(),"sdfgv");
        } else {
            super.onBackPressed();
        }
    }
}