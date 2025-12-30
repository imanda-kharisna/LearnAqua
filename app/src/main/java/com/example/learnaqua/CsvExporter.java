package com.example.learnaqua;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CsvExporter {

    public static void exportDatabaseToCsv(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        List<WaterLog> logs = db.waterDao().getAllLogs();

        String fileName = "WaterLog_" + System.currentTimeMillis() + ".csv";
        File folder = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(folder, fileName);

        try {
            FileWriter writer = new FileWriter(file);
            writer.append("ID,Amount(ml),Date,Time\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            for (WaterLog log : logs) {
                Date date = new Date(log.timestamp);
                writer.append(String.valueOf(log.id)).append(",")
                      .append(String.valueOf(log.amount)).append(",")
                      .append(dateFormat.format(date)).append(",")
                      .append(timeFormat.format(date)).append("\n");
            }

            writer.flush();
            writer.close();
            Toast.makeText(context, "Exported to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
