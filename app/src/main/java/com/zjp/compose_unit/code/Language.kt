package com.zjp.compose_unit.code

abstract class Language(val name: String) {

    abstract fun containsKeywords(word: String): Boolean
}