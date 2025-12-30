package com.example.learnaqua;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_logs")
public class WaterLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int amount;
    public long timestamp; // Waktu saat minum dalam milidetik

    public WaterLog(int amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
