package com.zjp.core_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zjp.core_database.dao.CollectionLikeDao
import com.zjp.core_database.dao.LikeDao
import com.zjp.core_database.model.CollectionLike
import com.zjp.core_database.model.LikeWidget

@Database(
    entities = [LikeWidget::class, CollectionLike::class], version = 2
)
abstract class ComposeDatabase : RoomDatabase() {

    abstract fun likeDao(): LikeDao
    abstract fun collectionLikeDao(): CollectionLikeDao

    companion object {
        @Volatile
        private var INSTANCE: ComposeDatabase? = null

        fun getInstance(context: Context): ComposeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ComposeDatabase::class.java,
                "like.db"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
    }
}