package com.zjp.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "collection", indices = [Index(value = arrayOf("name"), unique = true)])
data class Collection(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "nameCN") val nameCN: String,
    @ColumnInfo(name = "deprecated") val deprecated: Int? = 0,
    @ColumnInfo(name = "family") val family: Int,
    @ColumnInfo(name = "level") val level: Float,
    @ColumnInfo(name = "linkWidget") val linkWidget: String?,
    @ColumnInfo(name = "info") val info: String,
) : Serializable