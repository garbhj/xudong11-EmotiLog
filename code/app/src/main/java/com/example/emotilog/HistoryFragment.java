package com.example.emotilog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emotilog.databinding.FragmentHistoryBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment for HistoryFragment,
 * Purpose: displays summary of logged emoticons, and list of items, filtered by the selected date.
 *          and provides a button for changing the date.
 */
public class HistoryFragment extends Fragment implements DatePickerFragment.DatePickerDialogListener {

    private FragmentHistoryBinding binding;
    private EmotionLogProvider logProvider;
    private LocalDate selectedDate = LocalDate.now();

    private LogListAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logProvider = (EmotionLogProvider) context;  // host satisfies log provider interface
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment, return root
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /** Initialize UI components (button, list, summary) */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        adapter = new LogListAdapter(new ArrayList<>());

        binding.emotionsHistoryLog.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.emotionsHistoryLog.setAdapter(adapter);

        // The following three lines are from ChatGPT:
        // "Make getDateButton create a DatePickerFragment at the currently selected date, based on
        // the following implementation: <HistoryFragment implementation, omitted for brevity> <DatePickerFragment implementation, omitted for brevity>"
        // 2026-02-03
        binding.getDateButton.setOnClickListener(v ->
                DatePickerFragment.newInstance(selectedDate, this::onDatePicked).show(getParentFragmentManager(), "datePicker")
        );

        binding.dateText.setText("Selected Date: " + selectedDate.format(DateTimeFormatter.ofPattern("MMM. dd, YYYY")));
        loadForDate(selectedDate);  // Update UI to load data initially (current date)
    }

    /** Fetches logs for a given date and updates the ui components */
    private void loadForDate(LocalDate date) {
        List<EmotionLog> logs = logProvider.getEmotionLogsDate(date);

        adapter.updateData(logs);
        binding.summaryText.setText(buildSummary(logs));
    }

    /** Callback used by DatePickerDialogListener */
    public void onDatePicked(int year, int month, int day) {
        selectedDate = LocalDate.of(year, month + 1, day);
        binding.dateText.setText("Selected Date: " + selectedDate.format(DateTimeFormatter.ofPattern("MMM. dd, YYYY")));
        loadForDate(selectedDate);  // Update UI for new date
    }

    /** Generate formatted summary of emotion frequencies and counts */
    private String buildSummary(List<EmotionLog> logs) {
        if (logs.isEmpty()) {
            return "No emotions logged.";
        }

        // w3schools.com/java/java_hashmap.asp
        Map<String, Integer> counts = new HashMap<>();

        for (EmotionLog log : logs) {
            String emoji = log.getEmoji();
            if (counts.containsKey(emoji)) {
                counts.put(emoji, counts.get(emoji) + 1);  // Increments count if present, otherwise adds to dict
            } else {
                counts.put(emoji, 1);
            }
        }

        // Format the summary
        String summary = "Summary Counts:\nTotal: " + logs.size() + "\n";
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            summary = summary + entry.getKey() + "  :  " + String.format("%.0f%%", (double)entry.getValue() * 100 / logs.size()) + "  (recorded " + entry.getValue();
            if (entry.getValue() == 1) {
                summary += " time)\n";
            } else {
                summary += " times)\n";
            };
        }
        summary = summary.substring(0, summary.length() - 1);  // Remove trailing \n
        return summary;
    }


}