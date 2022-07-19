package com.zjp.collection.repository

import android.annotation.SuppressLint
import android.database.Cursor
import com.apkfuns.logutils.LogUtils
import com.zjp.common.data.Result
import com.zjp.core_database.ComposeEntry
import com.zjp.core_database.DBManager
import com.zjp.core_database.NodeEntry
import com.zjp.core_database.model.Collection
import com.zjp.core_database.model.CollectionNode

class CollectionRepository {
    private val dbManager: DBManager = DBManager.getInstance()

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
        const val COLLECTION_TABLE_NAME = "collection"
        const val NODE_TABLE_NAME = "collection_node"
    }


    fun getAllCollection(): Result<List<Collection>> {
        try {
            val cursor =
                dbManager.mDB.query(
                    COLLECTION_TABLE_NAME,
                    compose,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
                )
            val composes = mutableListOf<Collection>()

            while (cursor.moveToNext()) {
                composes.add(parseCollection(cursor))
            }
            cursor.close()
            LogUtils.d("获取数据成功")
            LogUtils.d(composes)
            return Result.Success(data = composes)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    fun getLinkCollection(links: Array<String>): Result<List<Collection>> {
        LogUtils.d("links:${links.toList()}")
        try {
            var sqlBuffer = StringBuffer("SELECT * FROM $COLLECTION_TABLE_NAME WHERE id IN (")
            links.forEach { linkId ->
                sqlBuffer.append("$linkId,")
            }
            sqlBuffer.append(")")
            sqlBuffer.delete(sqlBuffer.lastIndexOf(","), sqlBuffer.lastIndexOf(",") + 1)
            LogUtils.d(sqlBuffer)
            val cursor = dbManager.mDB.rawQuery(sqlBuffer.toString(), null)
            val composes = mutableListOf<Collection>()
            while (cursor.moveToNext()) {
                composes.add(parseCollection(cursor))
            }
            cursor.close()
            return Result.Success(data = composes)
        } catch (e: Exception) {
            LogUtils.e(e)
            return Result.Error(e)
        }
    }

    fun getCollectionById(id: Int): Collection? {
        try {
            val cursor =
                dbManager.mDB.query(
                    COLLECTION_TABLE_NAME,
                    compose,
                    composeSelection,
                    arrayOf("$id"),
                    null,
                    null,
                    sortOrder
                )
            if (cursor.moveToNext()) {
                val compose = parseCollection(cursor)
                cursor.close()
                return compose
            }
            cursor.close()
        } catch (e: Exception) {
            LogUtils.e(e)
        }
        return null
    }


    fun getNodesByCollectionId(composeId: Int): List<CollectionNode> {
        val cursor =
            dbManager.mDB.query(
                NODE_TABLE_NAME,
                node,
                nodeSelection,
                arrayOf("$composeId"),
                null,
                null,
                sortOrder
            )

        val nodes = mutableListOf<CollectionNode>()
        while (cursor.moveToNext()) {
            nodes.add(parseNode(cursor))
        }
        cursor.close()
        return nodes
    }

    @SuppressLint("Range")
    fun parseCollection(cursor: Cursor): Collection {

        return Collection(
            id = cursor.getInt(cursor.getColumnIndex(ComposeEntry.ID)),
            name = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME)),
            nameCN = cursor.getString(cursor.getColumnIndex(ComposeEntry.NAME_CN)),
            level = cursor.getFloat(cursor.getColumnIndex(ComposeEntry.LEVEL)),
            family = cursor.getInt(cursor.getColumnIndex(ComposeEntry.FAMILY)),
            info = cursor.getString(cursor.getColumnIndex(ComposeEntry.INFO)),
            linkWidget = cursor.getString(cursor.getColumnIndex(ComposeEntry.LINK_WIDGET)),
        )
    }

    @SuppressLint("Range")
    fun parseNode(cursor: Cursor): CollectionNode {

        return CollectionNode(
            id = cursor.getInt(cursor.getColumnIndex(NodeEntry.ID)),
            name = cursor.getString(cursor.getColumnIndex(NodeEntry.NAME)),
            code = cursor.getString(cursor.getColumnIndex(NodeEntry.CODE)),
            widgetId = cursor.getInt(cursor.getColumnIndex(NodeEntry.WIDGET_ID)),
            subtitle = cursor.getString(cursor.getColumnIndex(NodeEntry.SUBTITLE)),
            priority = cursor.getInt(cursor.getColumnIndex(NodeEntry.PRIORITY))
        )
    }
}