package com.zjp.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "collection_like")
data class CollectionLike(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "widget_id") val widgetId: Int,
) : Serializable