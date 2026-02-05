package com.example.emotilog;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for ensuring MainActivity implements the getter functions (not really necessary but good for safety)
 * */
public interface EmotionLogProvider {
    List<EmotionLog> getEmotionLogsAll();  // I guess this also needs
    List<EmotionLog> getEmotionLogsDate(LocalDate date);
}
