package com.geektech.newtodoapp.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.geektech.newtodoapp.models.Work;

import java.util.List;

@Dao
public interface WorkDao {
    @Query("SELECT*FROM work")
    LiveData<List<Work>> getAll();

    @Insert
    void insert(Work work);

}
