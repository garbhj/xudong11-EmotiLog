package com.example.emotilog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emotilog.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    // NOTE: See AddCityFragment
    private FragmentHomeBinding binding;
    private EmotionLogListener listener;

    public interface EmotionLogListener {
        // Methods needed for interaction between fragment and parent activity
        void onEmotionClick(EmotionButton button);
        List<EmotionButton> getEmotionButtons();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EmotionLogListener) {
            listener = (EmotionLogListener) context;  // = parent
        } else {
            throw new RuntimeException(context.toString() + " must implement EmotionLogListener");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // emojiGrid is the recycler view
        binding.emojiGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        List<EmotionButton> data = listener.getEmotionButtons();

        // Initialize adapter that forwards button clicks to main activity
        // NOTE: can refresh using adapter.notifyDataSetChanged()
        ButtonGridAdapter adapter = new ButtonGridAdapter(data, button -> {
            listener.onEmotionClick(button);
        });

        binding.emojiGrid.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}