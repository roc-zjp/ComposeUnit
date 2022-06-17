package com.zjp.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjp.core_database.model.LikeWidget
import com.zjp.core_database.model.Node


@Dao
interface LikeDao {
    @Query("SELECT * FROM like_widget WHERE widget_id LIKE :widgetId")
    fun getAllById(widgetId: Int): List<LikeWidget>

    @Delete
    fun delete(likeWidget: LikeWidget): Int

    @Insert
    fun insertAll(like: LikeWidget): Long

}