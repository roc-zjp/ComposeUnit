package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.Const
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.BuildConfig
import com.zjp.compose_unit.R
import com.zjp.compose_unit.common.shape.TechnoShapeBorder
import com.zjp.compose_unit.ui.home.ComposeItemView
import com.zjp.core_database.model.Compose
import com.zjp.system_composes.custom.shape.NStartShape
import com.zjp.system_composes.custom.shape.PolyShape
import com.zjp.system_composes.custom.shape.TicketShape


val itemCompose = Compose(0, "Image", "图片组件", 0, 0, 3.0f, "", "用于显示张图片")

@Composable
fun ItemSettingPage(goBack: () -> Unit = {}) {

    Scaffold(topBar = {
        UnitTopAppBar(title = { Text(text = "主题设置") }, navigationIcon = {
            IconButton(onClick = {
                goBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
        })
    }) {
        Box(modifier = Modifier.padding(it)) {
            Column {
                ComposeItem(compose = itemCompose, like = true, shape = TechnoShapeBorder())
                ComposeItem(compose = itemCompose, like = true, shape = CutCornerShape(32.dp))
            }
        }
    }
}

@Composable
fun ComposeItem(
    compose: Compose,
    like: Boolean,
    key: String? = null,
    highlighterColor: Color = Color.Red,
    shape: Shape = CutCornerShape(32.dp),
    onClick: () -> Unit = {}
) {

    val updateKey = rememberUpdatedState(newValue = key)
    var preLayoutResult: TextLayoutResult? by remember {
        mutableStateOf(null)
    }
    Box {
        Row(modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(shape)
            .border(
                1.dp, Const.colorDarkBlue, shape
            )
            .background(Const.colorDarkBlue.copy(0.25f))
            .clickable { onClick() }
            .padding(10.dp))
        {
            Box(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = compose.name.substring(IntRange(0, 1)),
                    style = TextStyle(
                        fontSize = 28.sp,
                        textAlign = TextAlign.End,
                        color = Const.colorDarkBlue,
                        shadow = Shadow(
                            color = Color.Gray, offset = Offset(5f, 5f), blurRadius = 5f
                        )
                    ),
                )

            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = compose.name,
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    onTextLayout = { layoutResult ->
                        preLayoutResult = layoutResult
                    },
                    modifier = Modifier.drawBehind {

                        if (preLayoutResult == null || updateKey.value.isNullOrEmpty()) {
                            return@drawBehind
                        }
                        updateKey.value?.let {
                            val startIndex = compose.name.lowercase().indexOf(it.lowercase())
                            val endIndex = startIndex + it.length

                            if (startIndex >= 0) {

                                for (index in startIndex until endIndex) {
                                    val boundsRect = preLayoutResult!!.getBoundingBox(index)
                                    drawRect(
                                        brush = SolidColor(highlighterColor),
                                        topLeft = boundsRect.topLeft,
                                        size = boundsRect.size
                                    )
                                }

                            }

                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = compose.info,
                    style = TextStyle(color = Color.Gray),
                    maxLines = 2,
                    color = Color(0xFF757575),
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (like) {
            Icon(
                painter = painterResource(id = R.drawable.collect),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp, top = 5.dp)
            )
        }
        if (BuildConfig.DEBUG) {
            Text(text = compose.id.toString(), modifier = Modifier.align(Alignment.CenterStart))
        }
    }
}


@Preview
@Composable
fun ItemPre() {
    Column() {
        ComposeItem(compose = itemCompose, like = true, shape = TechnoShapeBorder())
        ComposeItem(compose = itemCompose, like = true, shape = CutCornerShape(32.dp))
        ComposeItem(compose = itemCompose, like = true, shape = TicketShape(32f))
        ComposeItem(compose = itemCompose, like = true, shape = PolyShape(5,50f))
    }
}