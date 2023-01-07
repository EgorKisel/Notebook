package com.geekbrains.notebook.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notebook.R;
import com.geekbrains.notebook.repository.NoteData;
import com.geekbrains.notebook.repository.NoteSource;

import java.text.SimpleDateFormat;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private NoteSource noteSource;
    OnItemClickListener onItemClickListener;
    Fragment fragment;
    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }

    NoteAdapter(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    NoteAdapter() {
    }

    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater.inflate(R.layout.card_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, int position) {
        holder.bindContentWithLayout(noteSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final ImageView imageView;
        private final TextView textViewCapital;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.noteTitle);
            textViewDescription = itemView.findViewById(R.id.noteDescription);
            imageView = itemView.findViewById(R.id.noteMenu);
            textViewCapital = itemView.findViewById(R.id.capital);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuPosition = getLayoutPosition();
                }
            });

//            imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    menuPosition = getLayoutPosition();
//                    return false;
//                }
//            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    return false;
                }
            });

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
            fragment.registerForContextMenu(itemView);
        }

        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");

        public void bindContentWithLayout(NoteData content) {
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription());
            textViewCapital.setText(format.format(content.getDate()));
        }
    }
}
