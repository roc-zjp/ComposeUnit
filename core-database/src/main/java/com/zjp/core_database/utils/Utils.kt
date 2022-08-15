package com.zjp.compose_unit.data.utils

import android.annotation.SuppressLint
import android.database.Cursor
import com.zjp.core_database.ComposeEntry
import com.zjp.core_database.NodeEntry
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node

@SuppressLint("Range")
fun parseCompose(cursor: Cursor): Compose {
    val compose = Compose(
        id = cursor.getInt(cursor.getColumnIndex(ComposeEntry.ID)),
        name = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME)),
        nameCN = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME_CN)),
        level = cursor.getFloat(cursor.getColumnIndex(ComposeEntry.LEVEL)),
        family = cursor.getInt(cursor.getColumnIndex(ComposeEntry.FAMILY)),
        info = cursor.getString(cursor.getColumnIndex(ComposeEntry.INFO)),
        linkWidget = cursor.getString(cursor.getColumnIndex(ComposeEntry.LINK_WIDGET)),
    )

    return compose
}

@SuppressLint("Range")
fun parseNode(cursor: Cursor): Node {

    return Node(
        id = cursor.getInt(cursor.getColumnIndex(NodeEntry.ID)),
        name = cursor.getString(cursor.getColumnIndex(NodeEntry.NAME)),
        code = cursor.getString(cursor.getColumnIndex(NodeEntry.CODE)),
        widgetId = cursor.getInt(cursor.getColumnIndex(NodeEntry.WIDGET_ID)),
        subtitle = cursor.getString(cursor.getColumnIndex(NodeEntry.SUBTITLE)),
        priority = cursor.getInt(cursor.getColumnIndex(NodeEntry.PRIORITY))
    )
}