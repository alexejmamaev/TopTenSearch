package com.mamaevaleksej.toptensearch.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.mamaevaleksej.toptensearch.model.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String TAG = AppDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "search_results_database";
    private static AppDataBase sInstance;

    public static AppDataBase getsInstance(android.content.Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, AppDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract SearchResultDAO Dao();
}
