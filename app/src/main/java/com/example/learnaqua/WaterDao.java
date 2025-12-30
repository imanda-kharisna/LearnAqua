package com.example.learnaqua;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WaterDao {
    @Insert
    void insert(WaterLog log);

    @Query("SELECT SUM(amount) FROM water_logs WHERE timestamp >= :startOfDay")
    int getTotalWaterToday(long startOfDay);

    @Query("SELECT * FROM water_logs ORDER BY timestamp ASC")
    List<WaterLog> getAllLogs();
}
