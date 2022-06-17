package com.zjp.core_database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zjp.core_database.dao.ComposeDao
import com.zjp.core_database.dao.LikeDao
import com.zjp.core_database.dao.NodeDao
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.LikeWidget
import com.zjp.core_database.model.Node

@Database(
    entities = [Compose::class, Node::class,LikeWidget::class], version = 2
)
abstract class ComposeDatabase : RoomDatabase() {
    abstract fun composeDao(): ComposeDao
    abstract fun nodeDao(): NodeDao
    abstract fun likeDao(): LikeDao

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
                "compose.db"
            )
                .createFromAsset("compose.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }

}