package com.example.emotilog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Main data model for the application
 * Stores list of buttons, and list of logs, to be persistent between pages
 * References (Based on):
 * https://developer.android.com/topic/libraries/architecture/viewmodel#java
 */
public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<EmotionButton>> buttons = new MutableLiveData<>();
    private final MutableLiveData<List<EmotionLog>> logs = new MutableLiveData<>(new ArrayList<>());

    public MainViewModel() {
        // Called to reset buttons
        buttons.setValue(defaultButtons());
    }

    public LiveData<List<EmotionButton>> getButtons() {
        return buttons;
    }

    public LiveData<List<EmotionLog>> getLogs() {
        return logs;
    }

    public void logPress(EmotionButton button) {
        List<EmotionLog> current = new ArrayList<>(logs.getValue());
        current.add(new EmotionLog(button));
        logs.setValue(current);
    }

    private List<EmotionButton> defaultButtons() {
        return List.of(
                new EmotionButton("Happy", "\uD83D\uDE00"),  // 😀
                new EmotionButton("Sad", "\uD83D\uDE22"),  // 😢
                new EmotionButton("Angry", "\uD83D\uDE21"),  // 😡
                new EmotionButton("Anxious", "\uD83D\uDE28"),  // 😨
                new EmotionButton("Sleepy", "\uD83D\uDE34"),  // 😴
                new EmotionButton("Vibing", "\uD83D\uDE0E"),  // 😎
                new EmotionButton("Calm", "\uD83D\uDE0C"),  // 😌
                new EmotionButton("Bored", "\uD83D\uDE12"),  // 😒
                new EmotionButton("Energized", "⚡")
        );
    }
}
