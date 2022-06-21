package com.zjp.compose_unit

import android.app.Application
import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.ComposeDbHelper
import com.zjp.core_database.DBManager


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        ComposeDbHelper.init(this)
        DBManager.initDB(context = this)
        LocalDB.init(this)
    }

}