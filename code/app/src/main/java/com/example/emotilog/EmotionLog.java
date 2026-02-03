package com.example.emotilog;

import java.time.LocalDateTime;

public class EmotionLog {
    private final String title;
    private final String emoji;
    private final LocalDateTime timestamp;

    public EmotionLog(EmotionButton button) {
        this.title = button.getTitle();
        this.emoji = button.getEmoji();
        this.timestamp = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getEmoji() {
        return emoji;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
