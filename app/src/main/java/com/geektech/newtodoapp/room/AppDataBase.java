package com.geektech.newtodoapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.newtodoapp.models.Work;

@Database(entities = {Work.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract WorkDao workDao();

}

