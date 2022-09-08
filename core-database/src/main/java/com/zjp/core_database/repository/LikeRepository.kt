package com.zjp.core_database.repository

import com.zjp.core_database.ComposeDatabase
import com.zjp.core_database.LocalDB
import com.zjp.core_database.model.LikeWidget

class LikeRepository(private val db: ComposeDatabase = LocalDB.getDatabase()) {

    suspend fun getAllLike(): List<LikeWidget> {
        return db.likeDao().getAll()
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


    fun getLikeStatus(widgetId: Int): List<LikeWidget> {
        return db.likeDao().getAllById(widgetId)
    }

    private fun delete(widget: LikeWidget): Int {
        return db.likeDao().delete(widget)
    }


    private fun add(widgetId: Int): Long {
        return db.likeDao().insertAll(LikeWidget(widgetId = widgetId, id = 0))
    }


}