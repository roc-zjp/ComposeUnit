package com.zjp.compose_unit.common.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.LikeWidget
import com.zjp.core_database.model.Node
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DbViewModel : ViewModel() {

    private var _widgets = MutableLiveData(listOf<Compose>())
    val widgets: LiveData<List<Compose>> = _widgets


    private var _nodes = MutableLiveData(listOf<Node>())
    val nodes: LiveData<List<Node>> = _nodes

    private var _like = MutableLiveData(false)
    val like: LiveData<Boolean> = _like


    fun getAllWidget() {
        GlobalScope.launch {
            var list = LocalDB.getDatabase().composeDao().getAll()
            Log.d("TAG", "getAllWidget")
            _widgets.postValue(list)
        }
    }

    fun getNodesByWeightId(id: Int) {
        GlobalScope.launch {
            var list = LocalDB.getDatabase().nodeDao().getAllById(id)
            _nodes.postValue(list)
        }
    }

    fun likeStatus(id: Int) {
        GlobalScope.launch {
            var list = LocalDB.getDatabase().likeDao().getAllById(id)
            _like.postValue(list.isNotEmpty())
        }
    }

    fun like(id: Int) {
        GlobalScope.launch {
            var list = LocalDB.getDatabase().likeDao().getAllById(id)
            Log.d("TAG", list.toString())
            if (list.isEmpty()) {
                var result = LocalDB.getDatabase().likeDao().insertAll(LikeWidget(widgetId = id))
                Log.d("TAG", result.toString())
                _like.postValue(result >= 0)
            } else {
                var result = LocalDB.getDatabase().likeDao().delete(LikeWidget(widgetId = id))
                Log.d("TAG", result.toString())
                _like.postValue(result < 0)
            }
        }
    }


}