package com.eci.cosw.taskplanner.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.TypeConverters;

import com.eci.cosw.taskplanner.Model.Task;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
@TypeConverters(DateConverter.class)
public interface TaskDao {

    @Insert(onConflict = IGNORE)
    void insertTask(Task task);

}
