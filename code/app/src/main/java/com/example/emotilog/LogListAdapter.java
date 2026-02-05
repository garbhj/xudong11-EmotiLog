package com.example.emotilog;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotilog.databinding.LogItemCardBinding;

import java.lang.reflect.Method;
import java.util.List;

/**
 * RecyclerView Adapter for displaying list of EmotionLog items
 * Uses bindings to tie data fields to the card layout, RecyclerView handles the rest
 * This class is informed by information from the following:
 * https://developer.android.com/develop/ui/views/layout/recyclerview
 * https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 * https://www.digitalocean.com/community/tutorials/android-recyclerview-data-binding
*/
public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.ViewHolder> {
    private List<EmotionLog> logs;

    /** Constructor accepts the data */
    public LogListAdapter(List<EmotionLog> logs) {
        this.logs = logs;  // Reference to list in MainActivity
    }

    /** Method to notify adapter of changes to data being displayed */
    public void updateData(List<EmotionLog> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();  // TODO: Make updates more efficient
    }

    /** Creates a new ViewHolder that contains empty LogItemCard layout via binding */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogItemCardBinding binding = LogItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    /** Directly binds data to the ViewHolder (each parameter) */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmotionLog log = logs.get(position);
        holder.binding.cardTitle.setText(log.getTitle());
        holder.binding.cardEmoji.setText(log.getEmoji());
        holder.binding.cardDate.setText(log.getTimeStampString());
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    /** ViewHolder holding the binding reference; handled/recycled by RecyclerView */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LogItemCardBinding binding;

        public ViewHolder(LogItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
