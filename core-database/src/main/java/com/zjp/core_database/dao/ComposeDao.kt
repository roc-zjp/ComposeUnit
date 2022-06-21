package com.zjp.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjp.core_database.model.Compose

@Dao
public interface ComposeDao {
    @Query("SELECT * FROM compose")
    fun getAll(): List<Compose>?

    @Insert
    fun insertAll(compose: Array<Compose>)

    @Delete
    fun delete(compose: Compose)
}