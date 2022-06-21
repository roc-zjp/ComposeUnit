package com.zjp.compose_unit.repository

import android.annotation.SuppressLint
import android.database.Cursor
import com.zjp.core_database.ComposeDbHelper
import com.zjp.core_database.DBManager
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node

class ComposeRepository(private val dbManager: DBManager) {

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

    private val node = arrayOf(
        NodeEntry.ID,
        NodeEntry.NAME,
        NodeEntry.WIDGET_ID,
        NodeEntry.CODE,
        NodeEntry.SUBTITLE,
        NodeEntry.PRIORITY,
    )


    private val sortOrder = "${ComposeEntry.ID} ASC"

    private val composeSelection = "${ComposeEntry.ID} = ?"
    private val nodeSelection = "${NodeEntry.WIDGET_ID} = ?"


    companion object {
        const val COMPOSE_TABLE_NAME = "compose"
        const val NODE_TABLE_NAME = "Node"
    }


    fun getAllCompose(): List<Compose> {
        var cursor =
            dbManager.mDB.query(
                COMPOSE_TABLE_NAME,
                compose,
                null,
                null,
                null,
                null,
                sortOrder
            )
        var composes = mutableListOf<Compose>()

        while (cursor.moveToNext()) {
            composes.add(parseCompose(cursor))
        }
        cursor.close()
        return composes
    }

    fun getComposeById(id: Int): Compose? {
        var cursor =
            dbManager.mDB.query(
                COMPOSE_TABLE_NAME,
                compose,
                composeSelection,
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


    fun getNodeByWidgetId(composeId: Int): List<Node> {
        var cursor =
            dbManager.mDB.query(
                NODE_TABLE_NAME,
                node,
                nodeSelection,
                arrayOf("$composeId"),
                null,
                null,
                sortOrder
            )

        var nodes = mutableListOf<Node>()
        while (cursor.moveToNext()) {
            nodes.add(parseNode(cursor))
        }
        cursor.close()
        return nodes
    }

    @SuppressLint("Range")
    fun parseCompose(cursor: Cursor): Compose {
        var compose = Compose(
            id = cursor.getInt(cursor.getColumnIndex(ComposeEntry.ID)),
            name = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME)),
            nameCN = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME_CN)),
            level = cursor.getFloat(cursor.getColumnIndex(ComposeEntry.LEVEL)),
            family = cursor.getInt(cursor.getColumnIndex(ComposeEntry.FAMILY)),
            info = cursor.getString(cursor.getColumnIndex(ComposeEntry.INFO)),
            linkWidget = cursor.getString(cursor.getColumnIndex(ComposeEntry.LINK_WIDGET)),
        )
        cursor.close()
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

}