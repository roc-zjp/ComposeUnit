package com.zjp.core_database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.zjp.core_database.repository.ComposesRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.zjp.common.data.Result

class Database_Test {

    lateinit var composesRepository: ComposesRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        DBManager.initDB(context)
        composesRepository = ComposesRepository()
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


}