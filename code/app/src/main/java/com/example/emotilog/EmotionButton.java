package com.example.emotilog;

/**
 * Data model for button
 *
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
