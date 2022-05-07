package com.zjp.compose_unit.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Compose(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "nameCN") val nameCN: String,
    @ColumnInfo(name = "deprecated") val deprecated: Boolean = false,
    @ColumnInfo(name = "family") val family: Int,
    @ColumnInfo(name = "level") val level: Float,
    @ColumnInfo(name = "link_compose") val linkCompose: String,
    @ColumnInfo(name = "info") val info: String,
)