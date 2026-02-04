package com.example.emotilog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emotilog.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    // NOTE: See AddCityFragment, lab 3
    private FragmentHomeBinding binding;
    private EmotionLogListener buttonListener;
    private EmotionLogProvider logProvider;

    private LogListAdapter logAdapter;

    public interface EmotionLogListener {
        // Methods needed for interaction between fragment and parent activity
        void onEmotionClick(EmotionButton button);
        List<EmotionButton> getEmotionButtons();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EmotionLogListener && context instanceof EmotionLogProvider) {
            buttonListener = (EmotionLogListener) context;  // = parent
            logProvider = (EmotionLogProvider) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EmotionLogListener and EmotionLogProvider");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // emojiGrid is the recycler view
        binding.emojiGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        List<EmotionButton> buttons = buttonListener.getEmotionButtons();

        // Initialize adapter that forwards button clicks to main activity,
        // the View fragment_home holds any onClickListeners
        // NOTE: can refresh using adapter.notifyDataSetChanged()
        ButtonGridAdapter buttonAdapter = new ButtonGridAdapter(buttons, button -> {
            buttonListener.onEmotionClick(button);
        });
        binding.emojiGrid.setAdapter(buttonAdapter);


        binding.emotionsLog.setLayoutManager(new LinearLayoutManager(getContext()));

        List<EmotionLog> logs = logProvider.getEmotionLogsAll();
        LogListAdapter logAdapter = new LogListAdapter(logs);

        binding.emotionsLog.setAdapter(logAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}