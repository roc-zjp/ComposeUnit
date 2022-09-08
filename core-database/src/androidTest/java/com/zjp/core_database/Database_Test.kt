package com.zjp.core_database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.zjp.core_database.repository.ComposesRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.zjp.common.data.Result
import com.zjp.core_database.model.LikeWidget
import com.zjp.core_database.repository.LikeRepository

class Database_Test {

    lateinit var composesRepository: ComposesRepository
    lateinit var likeRepository: LikeRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        DBManager.initDB(context)
        LocalDB.init(context)
        composesRepository = ComposesRepository()
        likeRepository = LikeRepository()
    }

    @Test
    fun database_version() {
        assertEquals(DBManager.getInstance().mDB.version, 1)
        assertTrue(composesRepository.getAllCompose() is Result.Success)
    }

    @Test
    fun search() {
        val ids = composesRepository.search("icon").map {
            it.id
        }
        assertEquals(ids, listOf(15, 55))
    }


    @Test
    fun getAllLike() {

        LocalDB.getDatabase().likeDao().insertAll(LikeWidget(id = 1, widgetId = 2))
        LocalDB.getDatabase().likeDao().insertAll(LikeWidget(id = 2, widgetId = 1))
        val result = likeRepository.getAllLike()
        val ids = result.map { it.widgetId }.toList()
        assertEquals(listOf(2, 1), ids)
    }
}