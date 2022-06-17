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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.model.ComposeItem
import com.zjp.compose_unit.common.viewmodel.DbViewModel
import com.zjp.compose_unit.common.viewmodel.ShareViewModel

import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.core_database.model.Compose

@Composable
fun MainView(
    onClick: (compose: Compose) -> Unit,
    viewModel: ShareViewModel,
    dbViewModel: DbViewModel
) {
    val mList by dbViewModel.widgets.observeAsState()
    dbViewModel.getAllWidget()


    LazyColumn() {
        items<Compose>(mList!!, key = { it }) {
            ComposeItemView(compose = it) {
                onClick(it)
            }
        }
    }

}

@Composable
fun ComposeItemView(
    compose: Compose, spanWidth: Float = 15f,
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
                text = compose.name.substring(IntRange(0, 1)),
                style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.End)
            )
        }


        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = compose.name,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = compose.info,
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
            navController.navigate(Screen.ComposeDetailScreen.route)
        }, ShareViewModel(), DbViewModel())
    }
}