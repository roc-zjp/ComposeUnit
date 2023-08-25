package com.zjp.compose_unit.ui.developer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zjp.common.compose.RefreshLayout
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.common.utils.recomposeHighlighter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeveloperScreen(
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            UnitTopAppBar(
                title = { Text(text = "开发者页面") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = {
            RefreshLayout(
                refreshTriggerDp = 200.dp,
                loadTriggerDp = 200.dp,
                onRefresh = {


                },
                onLoadMore = {


                },
                modifier = Modifier
                    .padding(it)
                    .navigationBarsPadding(),

                ) { topPadding, bottomPadding ->
                LazyColumn(
                    modifier = Modifier
                        .recomposeHighlighter(),
                    contentPadding = PaddingValues(
                        top = topPadding,
                        bottom = bottomPadding
                    )
                ) {
                    items(100, key = { index ->
                        index
                    }) { index ->
                        Text(text = "Item $index", modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    )
}














