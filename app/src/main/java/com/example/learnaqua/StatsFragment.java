package com.example.learnaqua;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private TextView tvTotalMl;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        tvTotalMl = view.findViewById(R.id.tvTotalMl);
        barChart = view.findViewById(R.id.barChart);

        setupChart();
        loadStatistics();

        return view;
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getLegend().setEnabled(false);

        // Konfigurasi Sumbu X (Hari)
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        String[] days = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // Konfigurasi Sumbu Y
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
    }

    private void loadStatistics() {
        int[] dailyMl = {600, 800, 1000, 900, 1200, 700, 1100};

        int totalToday = dailyMl[dailyMl.length - 1];
        tvTotalMl.setText(totalToday + " ml");

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dailyMl.length; i++) {
            entries.add(new BarEntry(i, dailyMl[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Konsumsi Air");
        dataSet.setColor(Color.parseColor("#2196F3"));
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);

        barChart.setData(data);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}
