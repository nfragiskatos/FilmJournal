package com.create.nfraggle.filmjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Nicholas Fragiskatos on 4/18/2018.
 */
public class FrameCatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_catalog);

        // Setup FAB to open FilmRollEditActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



    }
}
