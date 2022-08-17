package com.zjp.collection.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.collection.repository.CollectionRepository
import com.zjp.common.data.Result
import com.zjp.common.state.CommonUiState
import com.zjp.core_database.model.Collection
import com.zjp.core_database.model.Compose
import kotlinx.coroutines.launch

data class CollectionState(
    val composes: List<Collection> = listOf(),
    val isLoading: Boolean = false,
    val errorMessages: List<String?> = emptyList(),
) {

    fun toUiState(): CommonUiState {
        return if (composes.isEmpty()) {
            CommonUiState.NoData(isLoading, errorMessages)
        } else {
            CommonUiState.HasData(composes, isLoading, errorMessages)
        }
    }

}


class CollectionViewModel : ViewModel() {
    private val repository = CollectionRepository()
    var uiState by mutableStateOf(CollectionState(isLoading = true).toUiState())

    init {
        refreshCollection()
    }

    private var collections = listOf<Collection>()

    private fun refreshCollection() {

        viewModelScope.launch {
            when (val result = repository.getAllCompose()) {
                is Result.Success -> {
                    collections = result.data
                    uiState = CollectionState(
                        composes = collections,
                        isLoading = false
                    ).toUiState()
                }
                is Result.Error -> {
                    collections = listOf()
                    uiState = CollectionState(
                        isLoading = false,
                        errorMessages = listOf(result.exception.message),
                    ).toUiState()
                }
            }
        }
    }
}