package com.darkwoods.notes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.darkwoods.notes.database.AppDatabase;
import com.darkwoods.notes.database.AppExecutors;
import com.darkwoods.notes.database.Note;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    // Extra for the note ID to be received in the intent
    public static final String EXTRA_NOTE_ID = "extraNoteId";
    // Extra for the note ID to be received after rotation
    public static final String INSTANCE_NOTE_ID = "instanceNoteId";

    private static final int DEFAULT_NOTE_ID = -1; //use on creation
    // Constant for logging
    private static final String TAG = AddNoteActivity.class.getSimpleName();
    // Fields for views
    EditText mTitleText;
    EditText mDescriptionText;

    private int mNoteId = DEFAULT_NOTE_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NOTE_ID)) {
            mNoteId = savedInstanceState.getInt(INSTANCE_NOTE_ID, DEFAULT_NOTE_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NOTE_ID)) {

            if (mNoteId == DEFAULT_NOTE_ID) {
                // populate the UI
                mNoteId = intent.getIntExtra(EXTRA_NOTE_ID, DEFAULT_NOTE_ID);

                 AddNoteViewModelFactory factory = new AddNoteViewModelFactory(mDb, mNoteId);
                final AddNoteViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);

                viewModel.getNote().observe(this, new Observer<Note>() {
                    @Override
                    public void onChanged(@Nullable Note note) {
                        viewModel.getNote().removeObserver(this);
                        populateUI(note);
                    }
                });
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_NOTE_ID, mNoteId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {


        mTitleText = findViewById(R.id.edit_note_title);
        mDescriptionText = findViewById(R.id.edit_note_description);


        // FAB for add or edit note
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });


    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param note the note to populate the UI
     */
    private void populateUI(Note note) {
        if (note == null) {
            return;
        }

        mTitleText.setText(note.getTitle());
        mDescriptionText.setText(note.getDescription());

    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new note data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String title = mTitleText.getText().toString().trim();
        String description = mDescriptionText.getText().toString().trim();
        Date date = new Date();

        final Note note = new Note(title, description, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mNoteId == DEFAULT_NOTE_ID) {
                    // insert new note
                    mDb.noteDao().insertNote(note);
                } else {
                    //update note
                    note.setId(mNoteId);
                    mDb.noteDao().updateNote(note);
                }
                finish();
            }
        });
    }



}
