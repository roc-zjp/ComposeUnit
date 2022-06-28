package com.zjp.compose_unit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zjp.compose_unit.data.repository.ComposesRepository
import com.zjp.compose_unit.data.repository.LikeRepository
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(val composeId: Int) : ViewModel() {
    private val repository = ComposesRepository()
    private val likeRepository = LikeRepository()

    val status = false

    var likeStatus by mutableStateOf(false)
    var compose by mutableStateOf<Compose?>(null)
    var nodes by mutableStateOf(listOf<Node>())

    init {
        viewModelScope.launch {
            compose = repository.getComposeById(composeId)
            nodes = repository.getNodesByWidgetId(composeId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            var result = likeRepository.getLikeStatus(composeId)
            withContext(Dispatchers.Main) {
                likeStatus = (result != null && result.isNotEmpty())
            }
        }
    }

    fun toggleLike() {
        viewModelScope.launch(Dispatchers.IO) {
            likeStatus = likeRepository.toggleLike(composeId = composeId)
        }
    }

}


class DetailViewModelFactory constructor(private val composeId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(composeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

