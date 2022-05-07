package com.zjp.compose_unit.repositories

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Compose::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun composeDao(): ComposeDao
}