package com.example.learnaqua;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WaterLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WaterDao waterDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "water_db")
                    .allowMainThreadQueries() // Untuk tutorial ini saja, idealnya pakai background thread
                    .build();
        }
        return instance;
    }
}
