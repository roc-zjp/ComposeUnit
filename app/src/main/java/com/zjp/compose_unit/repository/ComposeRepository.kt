package com.zjp.compose_unit.repository

import android.annotation.SuppressLint
import android.database.Cursor
import com.zjp.core_database.ComposeDbHelper
import com.zjp.core_database.model.Compose

class ComposeRepository(private val dbHelper: ComposeDbHelper) {

    private val compose = arrayOf(
        ComposeEntry.ID,
        ComposeEntry.NAME,
        ComposeEntry.NAME_CN,
        ComposeEntry.DEPRECATED,
        ComposeEntry.INFO,
        ComposeEntry.LEVEL,
        ComposeEntry.LINK_WIDGET,
        ComposeEntry.FAMILY
    )


    private val sortOrder = "${ComposeEntry.ID} DESC"

    val selection = "${ComposeEntry.ID} = ?"


    companion object {
        const val TABLE_NAME = "widget"
    }


    fun getAllCompose(): List<Compose> {
        var cursor =
            dbHelper.writableDatabase.query(TABLE_NAME, compose, null, null, null, null, sortOrder)
        cursor.moveToNext()
        var composes = mutableListOf<Compose>()

        while (cursor.moveToNext()) {
            composes.add(parseCompose(cursor))
        }
        return composes
    }

    fun getComposeById(id: Int): Compose? {
        var cursor =
            dbHelper.writableDatabase.query(
                TABLE_NAME,
                compose,
                selection,
                arrayOf("$id"),
                null,
                null,
                sortOrder
            )
        if (cursor.moveToNext()) {
            return parseCompose(cursor)
        }

        return null
    }

    @SuppressLint("Range")
    fun parseCompose(cursor: Cursor): Compose {

        return Compose(
            id = cursor.getInt(cursor.getColumnIndex(ComposeEntry.ID)),
            name = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME)),
            nameCN = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME_CN)),
            level = cursor.getFloat(cursor.getColumnIndex(ComposeEntry.LEVEL)),
            family = cursor.getInt(cursor.getColumnIndex(ComposeEntry.FAMILY)),
            info = cursor.getString(cursor.getColumnIndex(ComposeEntry.INFO)),
            linkWidget = cursor.getString(cursor.getColumnIndex(ComposeEntry.LINK_WIDGET)),
        )
    }

}