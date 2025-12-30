package com.example.learnaqua;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment extends Fragment {

    private LinearLayout containerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        containerLayout = view.findViewById(R.id.achievementContainer);

        List<Achievement> list = new ArrayList<>();
        list.add(new Achievement("First Drink", "Minum pertama kamu", true));
        list.add(new Achievement("Target Tercapai", "Capai 1200 ml hari ini", true));
        list.add(new Achievement("Pencari Embun", "Minum air sebelum jam 6 pagi", false));
        list.add(new Achievement("3 Hari Berturut", "Minum sesuai target 3 hari berturut", false));

        for (Achievement a : list) {
            View item = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_achievement, containerLayout, false);

            TextView title = item.findViewById(R.id.txtTitle);
            TextView desc = item.findViewById(R.id.txtDesc);
            ImageView status = item.findViewById(R.id.imgStatus);

            title.setText(a.title);
            desc.setText(a.desc);

            // Menggunakan ikon standar Android agar tidak error saat di-run
            status.setImageResource(
                    a.unlocked ? android.R.drawable.checkbox_on_background : android.R.drawable.ic_lock_idle_lock
            );

            containerLayout.addView(item);
        }

        return view;
    }
}
