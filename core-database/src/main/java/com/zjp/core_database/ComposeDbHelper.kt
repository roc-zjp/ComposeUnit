package com.zjp.core_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class ComposeDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("")
        onCreate(db)
        SQLiteDatabase.openOrCreateDatabase("",null)

    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "compose.db"

        @Volatile
        private var INSTANCE: ComposeDbHelper? = null


        fun getDbHelper(): ComposeDbHelper {
            if (INSTANCE == null) {
                throw Exception("使用前请调用init(context)方法")
            }
            return INSTANCE!!
        }

        fun init(context: Context) {
            INSTANCE = INSTANCE ?: synchronized(this) {
                INSTANCE ?: ComposeDbHelper(context)
            }
        }
    }
}