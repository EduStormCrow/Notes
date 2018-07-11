package com.darkwoods.notes.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class NoteVModel extends ViewModel {

    private LiveData<Note> noteLiveData;


        public NoteVModel(AppDatabase database, int noteID) {
            noteLiveData = database.noteDao().loadNoteById(noteID);
        }


    public LiveData<Note> getNoteLiveData() {
        return noteLiveData;
    }
}


