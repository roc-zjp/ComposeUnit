package com.zjp.compose_unit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.compose_unit.data.repository.ComposesRepository
import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.DBManager
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.LikeWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.zjp.common.data.Result
import com.zjp.common.state.CommonUiState


data class HomeState(
    val composes: List<Compose> = listOf(),
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

class HomeViewModel : ViewModel() {
    private val repository: ComposesRepository = ComposesRepository(DBManager.getInstance())
    private val viewModelState = MutableLiveData(HomeState(isLoading = true))

    var uiState by mutableStateOf(viewModelState.value!!.toUiState())


    private var composes = listOf<Compose>()
    var selectIndex by mutableStateOf(0)
    var likeCollects by mutableStateOf(listOf<LikeWidget>())

    init {
        refreshComposes()
    }

    private fun refreshComposes() {
        viewModelScope.launch {
            when (val result = repository.getAllCompose()) {
                is Result.Success -> {
                    composes = result.data as List<Compose>
                    viewModelState.value = HomeState(
                        composes = composes,
                        isLoading = false
                    )
                    uiState = viewModelState.value!!.toUiState()
                }
                is Result.Error -> {
                    composes = listOf()
                    viewModelState.value =
                        HomeState(
                            isLoading = false,
                            errorMessages = listOf(result.exception.message),
                        )
                    uiState = viewModelState.value!!.toUiState()
                }
            }
        }
    }

    fun updateLikeList() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = LocalDB.getDatabase().likeDao().getAll()
            withContext(Dispatchers.Main) {
                likeCollects = result
            }
        }
    }


    fun filter(index: Int) {
        selectIndex = index
        val results = composes.filter { compose ->
            index == 0 || compose.family == index
        }
        viewModelScope.launch {
            viewModelState.value =
                HomeState(
                    composes = results,
                    isLoading = false
                )
            uiState = viewModelState.value!!.toUiState()
        }
    }

}