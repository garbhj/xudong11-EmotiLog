package com.example.emotilog;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.ViewHolder> {
    private List<EmotionLog> logs = new ArrayList<>();

    public LogListAdapter(List<EmotionLog> logs) {
        this.logs = logs;
//        this.listener = listener;
    }

    // Method to update data being displayed
    public void updateData(List<EmotionLog> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.example.emotilog.databinding.LogItemCardBinding binding =
                com.example.emotilog.databinding.LogItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LogListAdapter.ViewHolder(binding);
    }

    // SETS the new text upon update via ViewHolder.bind
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmotionLog item = logs.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        View
         */

        private final com.example.emotilog.databinding.LogItemCardBinding binding;

        public ViewHolder(com.example.emotilog.databinding.LogItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final EmotionLog item) {
            binding.cardTitle.setText(item.getTitle());
            binding.cardEmoji.setText(item.getEmoji());
            binding.cardDate.setText(item.getTimeStampString());
        }

    }

}
