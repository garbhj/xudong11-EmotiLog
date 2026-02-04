package com.example.emotilog;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime getDateTime() {
        return timestamp;
    }

//    https://stackoverflow.com/questions/22463062/how-can-i-parse-format-dates-with-localdatetime-java-8
    public String getTimeStampString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        return timestamp.format(formatter); // "1986-04-08 12:30"
    }
}
