package com.zjp.compose_unit

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zjp.compose_unit.common.viewmodel.DbViewModel
import com.zjp.compose_unit.database.LocalDB
import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dbViewModel: DbViewModel = viewModel()

            Compose_unitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(dbViewModel = dbViewModel)
                }
            }
        }
//        GlobalScope.launch {
//            var list = LocalDB.getDatabase()?.composeDao()?.getAll()
//            Log.d("mainactivity", "list size=${list?.size}")
//        }
    }
}


@Preview(name = "Dark Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun DefaultPreview() {
    Compose_unitTheme {
        ListWithBug(arrayListOf<String>("das", "dadsa", "zjp"))
    }
}


@Composable
@Deprecated("Example with bug")
fun ListWithBug(myList: List<String>) {
    var items = 0

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item: $item")
                items++ // Avoid! Side-effect of the column recomposing.
            }
        }
        Text("Count: $items")
    }
}
