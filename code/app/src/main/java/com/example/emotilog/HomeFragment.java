package com.example.emotilog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emotilog.databinding.FragmentHomeBinding;
import com.example.emotilog.databinding.GridEmojiButtonBinding;

import java.util.List;

public class HomeFragment extends Fragment implements EmotionLogObserver {
    // NOTE: See AddCityFragment, lab 3
    private FragmentHomeBinding binding;
    private EmotionButtonListener buttonListener;
    private EmotionLogProvider logProvider;

    private LogListAdapter logAdapter;

    /**
     * Interface pattern for ensuring data communication (callback)
     * from HomeFragment to MainActivity upon EmotionButton click
     * (inspired by lab 3)
     * */
    public interface EmotionButtonListener {
        // Methods needed for interaction between fragment and activity
        void onEmotionClick(EmotionButton button);
        List<EmotionButton> getEmotionButtons();
    }

    /** Validate listener interfaces in host upon attaching to host */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EmotionButtonListener && context instanceof EmotionLogProvider) {
            buttonListener = (EmotionButtonListener) context;  // = parent
            logProvider = (EmotionLogProvider) context;
        } else {
            throw new RuntimeException(context + " must implement EmotionLogListener and EmotionLogProvider");
        }
        ((MainActivity) context).setLogObserver(this);  // emotionLogProvider has this
    }

    // Returns root view: FragmentHome
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialize UI components (button grid, list style recyclerView of emoticons) */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupEmojiGrid();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.emotionsLog.setLayoutManager(layoutManager);

        logAdapter = new LogListAdapter(logProvider.getEmotionLogsAll());  // Need instantiate
        binding.emotionsLog.setAdapter(logAdapter);
//        layoutManager.setReverseLayout(true);  // Reverse order with newest at top
    }

    /** Refreshes the log adapter, called when emotion logs updated */
    @Override
    public void onLogUpdated(List<EmotionLog> logs) {
        logAdapter.updateData(logs);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /** Create the button views; list provided by MainActivity */
    private void setupEmojiGrid() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        List<EmotionButton> buttons = buttonListener.getEmotionButtons();  // MainActivity provides buttons

        binding.emojiGrid.removeAllViews();  // Clean up in case duplicates

        // Loop through each object, expand layout, set fields, and add to the
        for (EmotionButton button : buttons) {
            GridEmojiButtonBinding buttonBinding = GridEmojiButtonBinding.inflate(inflater, binding.emojiGrid, false);
            // Note: buttonBinding is the reference to the grid_emoji_button

            buttonBinding.buttonEmoji.setText(button.getEmoji());
            buttonBinding.buttonTitle.setText(button.getTitle());

            buttonBinding.getRoot().setOnClickListener(v ->
                    buttonListener.onEmotionClick(button)  // click listener
            );

            binding.emojiGrid.addView(buttonBinding.getRoot());  // adds buttonBin
        }

    }
}