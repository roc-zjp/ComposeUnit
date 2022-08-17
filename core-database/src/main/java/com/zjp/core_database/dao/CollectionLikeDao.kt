package com.zjp.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjp.core_database.model.CollectionLike

@Dao
interface CollectionLikeDao {

    @Query("SELECT * FROM collection_like ")
    fun getAll(): List<CollectionLike>

    @Query("SELECT * FROM collection_like WHERE widget_id LIKE :widgetId")
    fun getAllById(widgetId: Int): List<CollectionLike>

    @Delete
    fun delete(likeWidget: CollectionLike): Int

    @Insert
    fun insertAll(like: CollectionLike): Long

}