package com.zjp.compose_unit.repository

import android.annotation.SuppressLint
import android.database.Cursor
import com.apkfuns.logutils.LogUtils
import com.zjp.compose_unit.Result
import com.zjp.core_database.DBManager
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node
import java.lang.Exception

class ComposeRepository(private val dbManager: DBManager = DBManager.getInstance()) {

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


    fun getAllCompose(): Result<List<Compose>> {
        try {
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
            LogUtils.d("获取数据成功")
            LogUtils.d(composes)
            return Result.Success(data = composes)
        } catch (e: Exception) {
            return Result.Error(e)
        }
        return Result.Error(Exception("获取数据失败"))
    }

    fun getComposeById(id: Int): Compose? {
        try {
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

            LogUtils.d("getComposeById")
            if (cursor.moveToNext()) {
                var compose = parseCompose(cursor)
                cursor.close()
                return compose
            }
            cursor.close()
        } catch (e: Exception) {
            LogUtils.e(e)
        }
        return null
    }


    fun getNodesByWidgetId(composeId: Int): List<Node> {
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