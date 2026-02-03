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
Inspired by:
https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
*/
public class ButtonGridAdapter extends RecyclerView.Adapter<ButtonGridAdapter.ViewHolder> {

    private List<EmotionButton> buttons = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EmotionButton button);
    }

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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_emoji_button, parent, false);  // Need
//        return new ViewHolder(view);
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

         */

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

//        TextView emoji, title;
//
//        ViewHolder(View view) {
//            super(view);
//            emoji = view.findViewById(R.id.button_emoji);
//            title = view.findViewById(R.id.button_title);
//        }
//
//        void bind(EmotionButton item, OnItemClickListener listener) {
//            emoji.setText(item.getEmoji());
//            title.setText(item.getTitle());
//
//            itemView.setOnClickListener(view -> listener.onItemClick(item));
//        }


    }
}
