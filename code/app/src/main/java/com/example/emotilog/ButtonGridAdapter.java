package com.example.emotilog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
Informed by:
https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
*/
public class ButtonGridAdapter extends RecyclerView.Adapter<ButtonGridAdapter.ViewHolder> {

    private List<EmotionButton> buttons = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EmotionButton button);
    }

    // NOTE: need to pass in a listener when init
    public ButtonGridAdapter(List<EmotionButton> buttons, OnItemClickListener listener) {
        this.buttons = buttons;
        this.listener = listener;
    }

    // Method to update data
    public void updateData(List<EmotionButton> newButtons) {
        this.buttons = newButtons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.example.emotilog.databinding.GridEmojiButtonBinding binding =
                com.example.emotilog.databinding.GridEmojiButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // SETS the new text upon update via ViewHolder.bind
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmotionButton item = buttons.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        * ViewHolder: Used by the RecyclerView to cycle bindings when scrolling
        * In reality the whole recyclerView probably isn't needed unless I implement adding buttons
        * */

        private final com.example.emotilog.databinding.GridEmojiButtonBinding binding;

        public ViewHolder(com.example.emotilog.databinding.GridEmojiButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final EmotionButton item, final OnItemClickListener listener) {
            binding.buttonEmoji.setText(item.getEmoji());
            binding.buttonTitle.setText(item.getTitle());

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
