package com.example.emotilog;

/**
 * Data object used to represent buttons in MainActivity
 * Stores the title and emoji, and provides getter functions
 */
public class EmotionButton {
    private String title;
    private String emoji;

    public EmotionButton(String title, String emoji) {
        this.emoji = emoji;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getEmoji() {
        return emoji;
    }
}
