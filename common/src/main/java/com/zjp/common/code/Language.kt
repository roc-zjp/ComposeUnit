package com.zjp.common.code

abstract class Language(val name: String) {

    abstract fun containsKeywords(word: String): Boolean
}