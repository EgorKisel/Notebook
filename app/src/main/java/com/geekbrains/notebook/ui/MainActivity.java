package com.geekbrains.notebook.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.geekbrains.notebook.R;
import com.geekbrains.notebook.publisher.Publisher;
import com.geekbrains.notebook.ui.main.MyDialogFragmentCustom;
import com.geekbrains.notebook.ui.main.NotebookFragment;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());

        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, NotebookFragment.newInstance()).commit();
            navigation.replaceFragment(NotebookFragment.newInstance(), false);
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