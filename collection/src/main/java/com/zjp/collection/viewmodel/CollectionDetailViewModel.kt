package com.zjp.collection.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zjp.collection.repository.CollectionLikeRepository
import com.zjp.collection.repository.CollectionRepository
import com.zjp.common.data.Result
import com.zjp.core_database.model.Collection
import com.zjp.core_database.model.CollectionNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectionDetailViewModel(private val composeId: Int) :
    ViewModel() {
    private val repository = CollectionRepository()
    private val likeRepository = CollectionLikeRepository()
    var likeStatus by mutableStateOf(false)
    var compose by mutableStateOf<Collection?>(null)
    var nodes by mutableStateOf(listOf<CollectionNode>())
    var links by mutableStateOf<List<Collection>?>(null)

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

    private fun queryLinkComposes(linkWidget: String?): List<Collection>? {
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


class DetailViewModelFactory constructor(private val composeId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionDetailViewModel::class.java)) {
            return CollectionDetailViewModel(composeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}