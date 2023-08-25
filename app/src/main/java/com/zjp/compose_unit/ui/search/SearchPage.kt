package com.zjp.compose_unit.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.ui.home.ComposeItemView
import com.zjp.core_database.model.Compose
import com.zjp.core_database.repository.ComposesRepository
import com.zjp.core_database.repository.LikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SearchPage(
    toComposeDetail: (id: Int) -> Unit,
    onBack: () -> Unit
) {
    var key by remember {
        mutableStateOf("")
    }
    var composes = remember { mutableStateListOf<Compose>() }
    var likeWidgets by remember {
        mutableStateOf(emptyList<Int>())
    }

    LaunchedEffect(key1 = key) {
        composes.clear()
        if (key.isNotEmpty()) {
            composes.addAll(ComposesRepository().search(key))
        }
    }
    var scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            var result = LikeRepository().getAllLike()
            likeWidgets = result.map { it.widgetId }.toList()
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            UnitTopAppBar(
                title = {
                    Box(modifier = Modifier.padding(end = 35.dp)) {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = com.zjp.common.R.drawable.search),
                                contentDescription = "search", tint = Color.Gray,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            BasicTextField(
                                value = key,
                                onValueChange = {
                                    key = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 30.dp)
                                    .align(Alignment.CenterVertically),
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Justify,
                                    fontSize = 20.sp,
                                    color = Color.Black.copy(alpha = 0.8f)
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(Color.Red),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                decorationBox = { innerTextField ->
                                    Box() {
                                        if (key.isEmpty()) {
                                            Text("搜点啥", color = Color.Gray, fontSize = 20.sp)
                                        }
                                        innerTextField()  //<-- Add this
                                    }
                                }
                            )
                        }
                        if (key.isNotEmpty()) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "close",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 10.dp)
                                    .clickable { key = "" },
                                tint = Color.Gray
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(it)
                .navigationBarsPadding()
        ) {
            val highlighterColor = LocalThemeColor.current
            LazyColumn() {
                items(composes) { compose ->
                    ComposeItemView(
                        compose = compose,
                        like = likeWidgets.contains(compose.id),
                        highlighterColor = highlighterColor,
                        key = key
                    ) {
                        toComposeDetail(compose.id)
                    }
                }
            }

        }
    }
}


