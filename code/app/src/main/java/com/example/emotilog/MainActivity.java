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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.EmotionLogListener, EmotionLogProvider {
    /*

     */
    // https://developer.android.com/topic/libraries/view-binding because it was never explained in pt 0
    private ActivityMainBinding binding;
    private final List<EmotionButton> buttons = new ArrayList<>();
    private final List<EmotionLog> logs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initButtons();
        initExampleLog();

        // Boilerplate: for aesthetics
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

    private void initExampleLog() {
        logs.add(new EmotionLog(buttons.get(0)));
        logs.add(new EmotionLog(buttons.get(1)));
    }

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

    @Override
    public void onEmotionClick(EmotionButton button) {
        logs.add(new EmotionLog(button));
        Toast.makeText(this, "Logged: " + button.getTitle(), Toast.LENGTH_SHORT).show();

    }

    public List<EmotionButton> getEmotionButtons() {
        return this.buttons;
    }

    public List<EmotionLog> getEmotionLogsAll() {
        return logs;
    }
}