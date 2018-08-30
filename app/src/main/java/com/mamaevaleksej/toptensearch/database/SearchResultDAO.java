package com.mamaevaleksej.toptensearch.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mamaevaleksej.toptensearch.model.Item;

import java.util.List;

@Dao
public interface SearchResultDAO {

    @Query("SELECT * FROM items ORDER BY id")
    LiveData<List<Item>> loadAllItems();

    @Query("SELECT * FROM items WHERE id = :id")
    LiveData<Item> loadItemById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(Item... items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Item orderEntry);

    @Query("DELETE FROM items WHERE id =:id")
    void deleteItem(int id);

}
