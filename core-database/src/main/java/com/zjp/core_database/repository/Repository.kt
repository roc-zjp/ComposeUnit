package com.zjp.core_database.repository

import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node
import com.zjp.common.data.Result

interface Repository {


    fun getAllCompose(): Result<List<Compose>>

    fun getLinkComposes(links: Array<String>): Result<List<Compose>>

    fun getComposeById(id: Int): Compose?

    fun getNodesByWidgetId(composeId: Int): List<Node>

}