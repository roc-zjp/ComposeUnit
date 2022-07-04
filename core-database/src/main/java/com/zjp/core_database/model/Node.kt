package com.zjp.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Node(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,

    @ColumnInfo(name = "widgetId")
    val widgetId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "priority")
    val priority: Int? = 0,

    @ColumnInfo(name = "subtitle")
    val subtitle: String,

    @ColumnInfo(name = "code")
    val code: String,
) : Serializable

