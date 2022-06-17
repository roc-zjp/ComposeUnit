package com.zjp.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zjp.core_database.model.Node

@Dao
interface NodeDao {
    @Query("SELECT * FROM node WHERE widgetId LIKE :widgetId")
    fun getAllById(widgetId: Int): List<Node>

    @Query("SELECT * FROM node WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Node>

    @Insert
    fun insertAll(nodes: Array<Node>)

    @Delete
    fun delete(node: Node)
}