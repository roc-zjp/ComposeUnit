package com.zjp.core_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.apkfuns.logutils.LogUtils
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader


class DBManager {

    lateinit var mDB: SQLiteDatabase

    companion object {
        const val COMPOSE_TABLE_NAME = "compose"
        const val NODE_TABLE_NAME = "Node"

        @Volatile
        private var INSTANCE: DBManager? = null

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "compose_tem.db"
        const val SP = "database_version"
        const val DATABASE_VERSION_SP = "version_sp"
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
            if (checkShouldCopy(context)) {
                copyAssets2Data(context)
            }
            DB_PATH = context.getDatabasePath(DATABASE_NAME).path
        }

        fun close() {
            INSTANCE?.mDB?.close()
            INSTANCE = null
        }

        private fun copyAssets2Data(context: Context) {
            var inputStream = context.assets.open("compose.db")
            var filePath = context.getDatabasePath(DBManager.DATABASE_NAME)
            if (filePath.exists()) {
                filePath.delete()
            }
            var fileOutPutStream = FileOutputStream(filePath);
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                fileOutPutStream.write(buffer, 0, length)
            }
            fileOutPutStream.flush()
            inputStream.close()
            fileOutPutStream.close()
            LogUtils.d(filePath);
            LogUtils.d("复制成功");
        }

        private fun checkShouldCopy(context: Context): Boolean {

            if (BuildConfig.DEBUG) {
                return true
            }

            var dbFile = context.getDatabasePath(DBManager.DATABASE_NAME)

            if (!dbFile.exists()) {
                return true
            }
            val oldVersion = context.getSharedPreferences(DBManager.SP, Context.MODE_PRIVATE)
                .getString(DBManager.DATABASE_VERSION_SP, "")
            var input = context.assets.open("version.txt")
            val bf = BufferedReader(InputStreamReader(input))
            var stringBuffer = StringBuffer()
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            if (!oldVersion.equals(stringBuffer.toString())) {
                return true
            }
            return false
        }

    }


}