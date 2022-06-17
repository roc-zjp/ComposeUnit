package com.zjp.compose_unit.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zjp.compose_unit.common.model.ComposeItem
import com.zjp.core_database.model.Compose


class ShareViewModel : ViewModel() {
    var composeItem by mutableStateOf<ComposeItem?>(null)
        private set
    var compose by mutableStateOf<Compose?>(null)
        private set

    fun addItem(item: ComposeItem) {
        composeItem = item
    }

    fun addCompose(item: Compose) {
        compose = item
    }

}