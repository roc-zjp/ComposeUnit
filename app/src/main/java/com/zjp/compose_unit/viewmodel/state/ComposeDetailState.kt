package com.zjp.compose_unit.viewmodel.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import com.zjp.core_database.model.Compose
import com.zjp.core_database.repository.ComposesRepository

class ComposeDetailState(private val composeId: Int?) {
    private val repository = ComposesRepository()
    var compose: Compose? = repository.getComposeById(composeId!!)
}


@Composable
fun rememberDetailState(composeId: Int?) = remember {
    ComposeDetailState(composeId)
}