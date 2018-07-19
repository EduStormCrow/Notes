package com.darkwoods.notes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.darkwoods.notes.database.AppDatabase;



public class AddNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final AppDatabase mDb;
    private final int mNoteId;


    public AddNoteViewModelFactory(AppDatabase database, int noteId) {
        mDb = database;
        mNoteId = noteId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddNoteViewModel(mDb, mNoteId);
    }
}
