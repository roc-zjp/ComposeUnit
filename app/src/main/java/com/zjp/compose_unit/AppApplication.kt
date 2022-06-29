package com.zjp.compose_unit

import android.app.Application
import com.zjp.compose_unit.database.LocalDB


class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalDB.init(this)
    }

}