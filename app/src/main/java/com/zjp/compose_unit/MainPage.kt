package com.zjp.compose_unit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.ui.theme.Compose_unitTheme

@Composable
fun MainView(onClick: (item: ComposeItem) -> Unit, viewModel: ShareViewModel) {
    var viewLists = mutableListOf<ComposeItem>()
    viewLists.add(
        ComposeItem(
            "Text",
            "用于显示文字的组件。拥有的属性非常多，足够满足你的使用需求，核心样式有Style属性样式控制。",
            detailPage = Screen.TextDetailScreen.route
        )
    )
    viewLists.add(
        ComposeItem(
            "TextField",
            "文字输入控件",
            detailPage = Screen.TextFieldDetailScreen.route
        )
    )
    viewLists.add(ComposeItem("Button", "用于点击组件", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Icon", "图标显示组件", detailPage = "text_detail_page"))

    viewLists.add(ComposeItem("Column", "竖向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Row", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(
        ComposeItem(
            "AnimatedVisibility",
            "横向布局组件，可以容纳多个组件",
            detailPage = "text_detail_page"
        )
    )
    viewLists.add(ComposeItem("Image", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Card", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(
        ComposeItem(
            "FloatingActionButton",
            "横向布局组件，可以容纳多个组件",
            detailPage = "text_detail_page"
        )
    )
    viewLists.add(ComposeItem("TopAppBar", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(
        ComposeItem(
            "ConstraintLayout",
            "横向布局组件，可以容纳多个组件",
            detailPage = "text_detail_page"
        )
    )
    viewLists.add(ComposeItem("IconButton", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Icon", "横向布局组件，可以容纳多个组件", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("animateDpAsState", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("OutlinedButton", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Scaffold", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(
        ComposeItem(
            "ProviderTextStyle",
            "过度动画，不属于view ",
            detailPage = "text_detail_page"
        )
    )
    viewLists.add(ComposeItem("Stack", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Spacer", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Button", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("LazyColumn", "过度动画，不属于view ", detailPage = "text_detail_page"))
    viewLists.add(ComposeItem("Canvas", "过度动画，不属于view ", detailPage = "text_detail_page"))


    LazyColumn() {
        items<ComposeItem>(viewLists, key = { it }) {
            ComposeItemView(composeItem = it) {
                onClick(it)
            }
        }
    }


}

@Composable
fun ComposeItemView(
    composeItem: ComposeItem, spanWidth: Float = 15f,
    innerRate: Float = 0.15f, onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(20.dp)
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Color(0xff4699FB), TechnoOutlineShape())
            .clip(TechnoOutlineShape())
            .drawWithContent {
                val path = Path().apply {
                    moveTo((size.width - size.width * innerRate) / 2, 0f)
                    relativeLineTo(size.width * innerRate, 0f)
                    relativeLineTo(-spanWidth, spanWidth)
                    relativeLineTo(-(size.width * innerRate - spanWidth * 2), 0f)
                    close()
                }

                this.drawContent()
            }
            .background(Color(0xFF00F1F1))
            .padding(10.dp)
    ) {

        Box(
            modifier = Modifier
                .size(80.dp, 80.dp)
                .border(1.dp, Color.White, CircleShape), contentAlignment = Alignment.Center
        ) {
            Text(
                text = composeItem.name.substring(IntRange(0, 1)),
                style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.End)
            )
        }


        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = composeItem.name,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = composeItem.description,
                style = TextStyle(color = Color.Gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(name = "Dark Mode")
@Composable
fun ComposeItemPreview() {
    Compose_unitTheme() {
        var navController = rememberNavController()

        MainView(onClick = {
            navController.navigate(it.detailPage)
        }, ShareViewModel())
    }
}