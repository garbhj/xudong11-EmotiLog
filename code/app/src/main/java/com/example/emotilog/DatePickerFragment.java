package com.example.emotilog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

/**
 * Date Picker Fragment using DatePickerDialog
 * Purpose: Used by HistoryFragment to pick a date, has interface to enforce callbacks.
 * Adapted from example in docs:
 * https://developer.android.com/develop/ui/views/components/pickers
 * */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialogListener listener;

    /** Interface required for attached View (host, being HistoryFragment) to be notified of date picked */
    public interface DatePickerDialogListener {
        void onDatePicked(int year, int month, int day);
    }

    /** Factory method to create new DatePickerFragment (See: newInstance pattern). */
    public static DatePickerFragment newInstance(LocalDate initialDate, DatePickerDialogListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.listener = listener;

        Bundle args = new Bundle();
        args.putInt("year", initialDate.getYear());
        args.putInt("month", initialDate.getMonthValue() - 1);
        args.putInt("day", initialDate.getDayOfMonth());
        fragment.setArguments(args);

        return fragment;
    }

    /** Creates and returns new DatePickerDialog with parameters */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = requireArguments();

        int year = args.getInt("year");
        int month = args.getInt("month");
        int day = args.getInt("day");

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    /** Callback for DatePickerDialog, which then calls back to the listener (LogListAdapter) to update */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (listener != null) {
            listener.onDatePicked(year, month, day);
        }
    }
}
