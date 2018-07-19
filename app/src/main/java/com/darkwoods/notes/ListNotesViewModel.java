package com.darkwoods.notes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.darkwoods.notes.database.AppDatabase;
import com.darkwoods.notes.database.Note;


import java.util.List;

public class ListNotesViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ListNotesViewModel.class.getSimpleName();

    private LiveData<List<Note>> notes;

    public ListNotesViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        notes = database.noteDao().loadAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}
