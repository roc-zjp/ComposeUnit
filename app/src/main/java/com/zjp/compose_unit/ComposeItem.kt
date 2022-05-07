package com.zjp.compose_unit

import java.io.Serializable

data class ComposeItem(
    val name: String,
    val description: String,
    val detailPage: String
) : Serializable