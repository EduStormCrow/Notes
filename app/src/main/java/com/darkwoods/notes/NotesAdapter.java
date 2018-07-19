package com.darkwoods.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darkwoods.notes.database.AppDatabase;
import com.darkwoods.notes.database.Note;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This TaskAdapter creates and binds ViewHolders, that hold the name and description of a note,
 * to a RecyclerView to efficiently display data.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";


    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    // Class variables for the List that holds notes data and the Context
    private List<Note> mNotesList;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private AppDatabase mDb;

    /**
     * Constructor for the NotesAdapter that initializes the Context.
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public NotesAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;

    }

    /**
     * Called when NotesViewHolder are created to fill a RecyclerView.
     * @return A new NotesViewHolder that holds the view for each task
     */
    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.notes_item_layout, parent, false);

        return new NotesViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        // Determine the values of the wanted data
        Note note = mNotesList.get(position);
        String title = note.getTitle();
        String description = note.getDescription();
        String updatedAt = dateFormat.format(note.getUpdateAt());

        //Set values
        holder.noteTitleView.setText(title);
        holder.noteDescriptionView.setText(description);
        holder.noteUpdateAtView.setText(updatedAt);
    }

     /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mNotesList == null) {
            return 0;
        }
        return mNotesList.size();
    }

    public List<Note> getNotes() {
        return mNotesList;
    }

    /**
     * When data changes, this method updates the list of Notes
     * and notifies the adapter to use the new values on it
     */
    public void setNotes(List<Note> listNotes) {
        mNotesList = listNotes;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
        void onLongClickListener(Note note);
    }


    // Inner class for creating ViewHolders
    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        // Class variables for the note name and description
        TextView noteTitleView;
        TextView noteUpdateAtView;
        TextView noteDescriptionView;

        /**
         * Constructor for the NotesViewHolders.
         * @param itemView The view inflated in onCreateViewHolder
         */
        public NotesViewHolder(View itemView) {
            super(itemView);
            noteTitleView = itemView.findViewById(R.id.note_title);
            noteUpdateAtView = itemView.findViewById(R.id.note_update_at);
            noteDescriptionView = itemView.findViewById(R.id.note_description);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        
        @Override
        public void onClick(View view) {
            int elementId = mNotesList.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }

        @Override
        public boolean onLongClick(View view) {
            Note mNote = mNotesList.get(getAdapterPosition());
            mItemClickListener.onLongClickListener(mNote);
            return true;
        }
    }
}
