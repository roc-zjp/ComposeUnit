package com.zjp.core_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import com.apkfuns.logutils.LogUtils;

class ComposeDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
       LogUtils.d("database onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "compose_tem.db"
        private const val SP = "database_version"
        private const val DATABASE_VERSION_SP = "version_sp"

        @Volatile
        private var INSTANCE: ComposeDbHelper? = null

        fun getDbHelper(): ComposeDbHelper {
            if (INSTANCE == null) {
                throw Exception("使用前请调用init(context)方法")
            }
            return INSTANCE!!
        }

        fun init(context: Context) {
            if (checkShouldCopy(context)) {
                copyAssets2Data(context)
            }
            INSTANCE = INSTANCE ?: synchronized(this) {
                INSTANCE ?: ComposeDbHelper(context)
            }
        }

        private fun copyAssets2Data(context: Context) {
            val inputStream = context.assets.open("compose.db")
            val filePath = context.getDatabasePath(DATABASE_NAME)
            if (filePath.exists()) {
                filePath.delete()
            }
            val fileOutPutStream = FileOutputStream(filePath);
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                fileOutPutStream.write(buffer, 0, length)
            }
            inputStream.close()
            fileOutPutStream.close()
            LogUtils.d(filePath);
            LogUtils.d("复制成功");
        }

        private fun checkShouldCopy(context: Context): Boolean {

            if (BuildConfig.DEBUG) {
                return true
            }

            val oldVersion = context.getSharedPreferences(SP, Context.MODE_PRIVATE)
                .getString(DATABASE_VERSION_SP, "")
            val input = context.assets.open("version.txt")
            val bf = BufferedReader(InputStreamReader(input))

            val stringBuffer = StringBuffer()
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