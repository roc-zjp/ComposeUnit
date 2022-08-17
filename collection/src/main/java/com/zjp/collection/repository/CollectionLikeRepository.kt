package com.zjp.collection.repository


import com.zjp.core_database.ComposeDatabase
import com.zjp.core_database.model.LikeWidget
import com.zjp.common.data.Result
import com.zjp.core_database.LocalDB
import com.zjp.core_database.model.CollectionLike

class CollectionLikeRepository(private val db: ComposeDatabase = LocalDB.getDatabase()) {

    fun getAllLike(): Result<List<CollectionLike>> {
        return try {
            val list = LocalDB.getDatabase().collectionLikeDao().getAll()
            Result.Success(list)
        } catch (e: Exception) {
            Result.Error(Exception("获取收藏数据失败"))
        }
        db.collectionLikeDao().getAll()
    }


    fun toggleLike(composeId: Int): Boolean {
        val likeWidgets = getLikeStatus(composeId)
        return if (likeWidgets.isEmpty()) {
            val result = add(composeId)
            result >= 0
        } else {
            val result = delete(likeWidgets.first())
            result <= 0
        }
    }


    fun getLikeStatus(widgetId: Int): List<CollectionLike> {
        return db.collectionLikeDao().getAllById(widgetId)
    }

    private fun delete(widget: CollectionLike): Int {
        return db.collectionLikeDao().delete(widget)
    }


    private fun add(widgetId: Int): Long {
        return db.likeDao().insertAll(LikeWidget(widgetId = widgetId, id = 0))
    }


}