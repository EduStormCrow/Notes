package com.darkwoods.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.darkwoods.notes.database.AppDatabase;
import com.darkwoods.notes.database.Note;


public class AddNoteViewModel extends ViewModel {

    private LiveData<Note> note;


    public AddNoteViewModel(AppDatabase database, int noteId) {
        note = database.noteDao().loadNoteById(noteId);
    }


    public LiveData<Note> getNote() {
        return note;
    }
}
