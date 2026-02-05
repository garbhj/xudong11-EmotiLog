package com.example.emotilog;

import java.util.List;

/**
 * Interface pattern for ensuring data communication (callbacks)
 * from MainActivity to HomeFragment upon update to emotion logs
 * (inspired by lab 3)
 * */
public interface EmotionLogObserver {
    void onLogUpdated(List<EmotionLog> logs);
}
