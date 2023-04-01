package com.zjp.core_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader


class DBManager {

    lateinit var mDB: SQLiteDatabase

    companion object {
        @Volatile
        private var INSTANCE: DBManager? = null
        private const val DATABASE_NAME = "compose_tem.db"
        private const val SP = "database_version"
        private const val DATABASE_VERSION_SP = "version_sp"
        private lateinit var DB_PATH: String


        fun getInstance(): DBManager {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = DBManager()
                        INSTANCE?.mDB = SQLiteDatabase.openDatabase(
                            DB_PATH, null, SQLiteDatabase.OPEN_READWRITE
                        )
                    }
                }
            }
            return INSTANCE!!
        }

        fun initDB(context: Context) {
            var appBuiltInVersion = appBuiltInVersion(context)
            if (checkShouldCopy(context, appBuiltInVersion)) {
                copyAssets2Data(context)
                saveNewVersion(context, appBuiltInVersion)
            }
            DB_PATH = context.getDatabasePath(DATABASE_NAME).path
        }


        private fun copyAssets2Data(context: Context) {
            val inputStream = context.assets.open("compose.db")
            val filePath = context.getDatabasePath(DBManager.DATABASE_NAME)
            if (filePath.exists()) {
                filePath.delete()
            }
            val fileOutPutStream = FileOutputStream(filePath);
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                fileOutPutStream.write(buffer, 0, length)
            }
            fileOutPutStream.flush()
            inputStream.close()
            fileOutPutStream.close()

        }

        private fun saveNewVersion(context: Context, version: String) {
            context.getSharedPreferences(SP, Context.MODE_PRIVATE).edit()
                .putString(DATABASE_VERSION_SP, version)
        }

        private fun appBuiltInVersion(context: Context): String {
            val input = context.assets.open("version.txt")
            val bf = BufferedReader(InputStreamReader(input))
            val stringBuffer = StringBuffer()
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            return stringBuffer.toString()
        }

        private fun checkShouldCopy(context: Context, appBuiltInVersion: String): Boolean {
            if (BuildConfig.DEBUG) {
                return true
            }

            val dbFile = context.getDatabasePath(DBManager.DATABASE_NAME)

            if (!dbFile.exists()) {
                return true
            }
            val oldVersion = context.getSharedPreferences(DBManager.SP, Context.MODE_PRIVATE)
                .getString(DBManager.DATABASE_VERSION_SP, "")

            if (!oldVersion.equals(appBuiltInVersion)) {
                return true
            }
            return false
        }

    }


}