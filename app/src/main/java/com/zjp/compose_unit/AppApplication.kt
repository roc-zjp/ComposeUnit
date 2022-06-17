package com.zjp.compose_unit

import android.app.Application
import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.ComposeDbHelper


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        ComposeDbHelper.init(this)
        LocalDB.init(this)
    }

}