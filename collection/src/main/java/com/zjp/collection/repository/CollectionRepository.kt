package com.zjp.collection.repository

import com.apkfuns.logutils.LogUtils
import com.zjp.common.data.Result
import com.zjp.core_database.CollectEntry
import com.zjp.core_database.CollectionNodeEntry
import com.zjp.core_database.DBManager
import com.zjp.core_database.model.Collection
import com.zjp.core_database.model.CollectionNode
import com.zjp.core_database.utils.parseCollection
import com.zjp.core_database.utils.parseCollectionNode

class CollectionRepository(private val dbManager: DBManager = DBManager.getInstance()) {

    private val collection = arrayOf(
        CollectEntry.ID,
        CollectEntry.NAME,
        CollectEntry.NAME_CN,
        CollectEntry.DEPRECATED,
        CollectEntry.INFO,
        CollectEntry.LEVEL,
        CollectEntry.LINK_WIDGET,
        CollectEntry.FAMILY,
        CollectEntry.IMG,
    )

    private val node = arrayOf(
        CollectionNodeEntry.ID,
        CollectionNodeEntry.NAME,
        CollectionNodeEntry.WIDGET_ID,
        CollectionNodeEntry.CODE,
        CollectionNodeEntry.SUBTITLE,
        CollectionNodeEntry.PRIORITY,
    )


    private val sortOrder = "${CollectEntry.ID} ASC"

    private val collectionSelection = "${CollectEntry.ID} = ?"
    private val nodeSelection = "${CollectionNodeEntry.WIDGET_ID} = ?"


    companion object {
        const val COMPOSE_TABLE_NAME = "collection"
        const val NODE_TABLE_NAME = "collection_node"
    }

    fun getAllCompose(): Result<List<Collection>> {
        try {
            val cursor =
                dbManager.mDB.query(
                    COMPOSE_TABLE_NAME,
                    collection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
                )
            val collections = mutableListOf<Collection>()

            while (cursor.moveToNext()) {
                collections.add(parseCollection(cursor))
            }
            cursor.close()
            return Result.Success(data = collections)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    fun getLinkComposes(links: Array<String>): Result<List<Collection>> {
        LogUtils.d("links:${links.toList()}")
        try {
            var sqlBuffer =
                StringBuffer("SELECT * FROM $COMPOSE_TABLE_NAME WHERE id IN (")
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

    fun getComposeById(id: Int): Collection? {
        try {
            val cursor =
                dbManager.mDB.query(
                    COMPOSE_TABLE_NAME,
                    collection,
                    collectionSelection,
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


    fun getNodesByWidgetId(composeId: Int): List<CollectionNode> {
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
            nodes.add(parseCollectionNode(cursor))
        }
        cursor.close()
        return nodes
    }


}