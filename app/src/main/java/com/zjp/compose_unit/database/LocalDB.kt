package com.zjp.compose_unit.database

import android.content.Context
import com.zjp.core_database.ComposeDatabase
import java.lang.Exception

class LocalDB {
    companion object {
        private lateinit var _database: ComposeDatabase

        fun getDatabase(): ComposeDatabase {
            if (!this::_database.isInitialized) {
                throw  Exception("使用数据库之前，请先调用init方法")
            }
            return _database;
        }

        private lateinit var applicationContext: Context

        fun init(context: Context) {
            applicationContext = context
            _database = ComposeDatabase.getInstance(context)
        }

    }
}