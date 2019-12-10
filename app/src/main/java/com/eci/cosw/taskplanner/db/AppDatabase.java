package com.eci.cosw.taskplanner.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.eci.cosw.taskplanner.Model.Task;
import com.eci.cosw.taskplanner.Model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Task.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TaskDao taskModel();

    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(1);

    public static AppDatabase getInMemoryDatabase(final Context context) {
        if (INSTANCE == null) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .allowMainThreadQueries()
                            .build();
                }
            });
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
