package com.zjp.compose_unit.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zjp.common.data.Result
import com.zjp.compose_unit.data.repository.CollectionRepository
import com.zjp.compose_unit.data.repository.ComposesRepository
import com.zjp.compose_unit.data.repository.LikeRepository
import com.zjp.compose_unit.data.repository.Repository
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node
import kotlinx.coroutines.*

class DetailViewModel(private val composeId: Int, private val type: PageType) : ViewModel() {
    private val repository: Repository =
        if (type == PageType.COLLECTION) CollectionRepository() else ComposesRepository()
    private val likeRepository = LikeRepository()

    var likeStatus by mutableStateOf(false)
    var compose by mutableStateOf<Compose?>(null)
    var nodes by mutableStateOf(listOf<Node>())
    var links by mutableStateOf<List<Compose>?>(null)

    var tips by mutableStateOf(false)

    init {

        viewModelScope.launch {
            compose = repository.getComposeById(composeId)
            links = queryLinkComposes(compose?.linkWidget)
            nodes = repository.getNodesByWidgetId(composeId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = likeRepository.getLikeStatus(composeId)
            withContext(Dispatchers.Main) {
                likeStatus = (result != null && result.isNotEmpty())
            }
        }
    }

    private fun queryLinkComposes(linkWidget: String?): List<Compose>? {
        if (linkWidget == null || linkWidget.isEmpty()) {
            return null
        }
        var linkIds = linkWidget.split(",")
        var result = repository.getLinkComposes(linkIds.toTypedArray())
        if (result is Result.Error) {
            return null
        } else if (result is Result.Success) {
            return result.data
        }
        return null
    }

    fun toggleLike() {
        viewModelScope.launch(Dispatchers.IO) {
            likeStatus = likeRepository.toggleLike(composeId = composeId)
            showLikeResult(likeStatus)
        }
    }

    private suspend fun showLikeResult(like: Boolean) {
        tips = true
        delay(3000)
        tips = false
    }
}


class DetailViewModelFactory constructor(private val composeId: Int, private val type: PageType) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(composeId, type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


enum class PageType {
    COMPOSE, COLLECTION
}


fun PageType.name(): String {
    return if (this == PageType.COLLECTION) {
        "Collection"
    } else {
        "Compose"
    }
}

fun String.toPageType(): PageType {
    return if (this == "Collection") {
        PageType.COLLECTION
    } else {
        PageType.COMPOSE
    }
}



