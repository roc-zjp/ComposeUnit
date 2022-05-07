package com.zjp.compose_unit.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ComposeDao {
    @Query("SELECT * FROM compose")
    fun getAll(): List<Compose>

    @Query("SELECT * FROM compose WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Compose>


    @Insert
    fun insertAll(compose: Array<Compose>)

    @Delete
    fun delete(compose: Compose)
}