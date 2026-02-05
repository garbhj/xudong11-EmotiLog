package com.example.emotilog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Data object used to represent logged emoticons in MainActivity
 * Provides getter functions for parameters, including for formatted date.
 * */
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

    /**
     * Returns formatted string of date in format e.g. Jan. 23, 04:56:00 PM
     * https://stackoverflow.com/questions/22463062/how-can-i-parse-format-dates-with-localdatetime-java-8
      */
    public String getTimeStampString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. dd, hh:mm:ss a");
        return timestamp.format(formatter);
    }

    /** Returns LocalDateUsed for comparison to retrieve logs of one day in MainActivity */
    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }
}
