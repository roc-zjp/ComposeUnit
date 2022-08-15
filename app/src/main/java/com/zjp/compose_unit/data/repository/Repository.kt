package com.zjp.compose_unit.data.repository

import com.zjp.common.data.Result
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node

interface Repository {


    fun getAllCompose(): Result<List<Compose>>

    fun getLinkComposes(links: Array<String>): Result<List<Compose>>

    fun getComposeById(id: Int): Compose?

    fun getNodesByWidgetId(composeId: Int): List<Node>

}