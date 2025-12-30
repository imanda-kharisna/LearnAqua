package com.example.learnaqua;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingFragment extends Fragment {

    private MaterialSwitch switchDark, switchSleep;
    private CardView cardExport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        switchDark = view.findViewById(R.id.switchDark);
        switchSleep = view.findViewById(R.id.switchSleep);
        cardExport = view.findViewById(R.id.cardExport);

        SharedPreferences prefs =
                requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        // LOAD STATE
        switchDark.setChecked(prefs.getBoolean("dark_mode", false));
        switchSleep.setChecked(prefs.getBoolean("sleep_mode", false));

        // DARK MODE
        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ?
                            AppCompatDelegate.MODE_NIGHT_YES :
                            AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        // MODE TIDUR (ALARM)
        switchSleep.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("sleep_mode", isChecked).apply();
            if (isChecked) {
                ReminderManager.setReminder(requireContext());
            } else {
                ReminderManager.cancelReminder(requireContext());
            }
        });

        // EXPORT CSV
        cardExport.setOnClickListener(v -> {
            CsvExporter.exportDatabaseToCsv(requireContext());
        });

        return view;
    }
}
