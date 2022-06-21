package com.zjp.compose_unit.repository

import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.LikeWidget
import com.zjp.core_database.model.Node

class ComposeDetailRepository(private val compose: Compose) {

    private val likeDao = LocalDB.getDatabase().likeDao()


    fun getLikeStatus(): Boolean {
        val result = likeDao.getAllById(compose.id)
        return result.isNotEmpty()
    }

    fun like(): Boolean {
        val status = getLikeStatus()
        return if (!status) {
            var result =
                LocalDB.getDatabase().likeDao().insertAll(LikeWidget(widgetId = compose.id))
            result >= 0
        } else {
            var result = LocalDB.getDatabase().likeDao().delete(LikeWidget(widgetId = compose.id))
            result <= 0
        }
    }

}