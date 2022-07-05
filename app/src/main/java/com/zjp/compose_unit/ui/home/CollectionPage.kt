package com.zjp.compose_unit.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CollectionPage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
    ) {
        Text(text = "集合", Modifier.align(Alignment.Center))
    }
}