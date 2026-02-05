package com.example.emotilog;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.emotilog.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity
 * Purpose: Stores and manages the buttons and logged emotions as lists
 *          Provides methods to modify the data
 *          Establishes the bottom NavigationBar
 * Design:  Provides interface with Fragments (callbacks) to update data/views on both sides
 *          (also keeps track of and updates an EmotionLogObserver
 * NOTE: This project uses view bindings exclusively instead of findViewById
 */
public class MainActivity extends AppCompatActivity implements HomeFragment.EmotionButtonListener, EmotionLogProvider {
    // https://developer.android.com/topic/libraries/view-binding
    private ActivityMainBinding binding;
    // NOTE: The recommended declaration w/ List interface for flexibility w/ ArrayList, LinkedList, etc.
    // https://stackoverflow.com/questions/9852831/polymorphism-why-use-list-list-new-arraylist-instead-of-arraylist-list-n
    private final List<EmotionButton> buttons = new ArrayList<>();
    private final List<EmotionLog> logs = new ArrayList<>();
    private EmotionLogObserver observer;


    /** Initializes view binding, data structure, and navigation bar w/ controller */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initButtons();

        // Boilerplate: included in BasicViewsActivity
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Two step recommended for bottom navbar in: https://developer.android.com/guide/navigation/integrations/ui#java
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        BottomNavigationView bottomNavBar = binding.bottomNavBar;
        assert navHostFragment != null;  // Autofill
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavBar, navController);
    }

    /** Create examples for testing */
    private void initExampleLog() {
        logs.add(new EmotionLog(buttons.get(0)));
        logs.add(new EmotionLog(buttons.get(1)));
    }

    /** Initialize the nine predefined buttons */
    private void initButtons() {
        buttons.add(new EmotionButton("Happy", "\uD83D\uDE00"));  // 😀
        buttons.add(new EmotionButton("Sad", "\uD83D\uDE22"));  // 😢
        buttons.add(new EmotionButton("Angry", "\uD83D\uDE21"));  // 😡
        buttons.add(new EmotionButton("Anxious", "\uD83D\uDE28"));  // 😨
        buttons.add(new EmotionButton("Sleepy", "\uD83D\uDE34"));  // 😴
        buttons.add(new EmotionButton("Vibing", "\uD83D\uDE0E"));  // 😎
        buttons.add(new EmotionButton("Calm", "\uD83D\uDE0C"));  // 😌
        buttons.add(new EmotionButton("Bored", "\uD83D\uDE12"));  // 😒
        buttons.add(new EmotionButton("Energized", "⚡"));
    }

    /** Sets the observer, which is HomeFragment, for callback (EmotionLogProvider.onEmotionClick). */
    public void setLogObserver(EmotionLogObserver observer) {
        this.observer = observer;
    }

    /** Callback: Updates log upon button press, then immediately notifies observer of change. */
    @Override
    public void onEmotionClick(EmotionButton button) {
        logs.add(0, new EmotionLog(button));
        if (observer != null) {
            observer.onLogUpdated(logs);
        }
        //        Toast.makeText(this, "Logged: " + button.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public List<EmotionButton> getEmotionButtons() {
        return this.buttons;  // Returns list of all emotion buttons
    }

    public List<EmotionLog> getEmotionLogsAll() {
        return logs;  // Returns all emotion log entries
    }

    public List<EmotionLog> getEmotionLogsDate(LocalDate date) {
        // Returns emotion log entries matching the date given
        List<EmotionLog> result = new ArrayList<>();

        for (EmotionLog log : logs) {
            if (log.getDate().equals(date)) {
                result.add(log);
            }
        }
        return result;
    }
}