package com.zjp.compose_unit.code

class KotlinLanguage : Language("kotlin") {

    companion object {
        val kKeywords: Array<String> = arrayOf(
            "abstract", "assert", "break", "case", "catch", "class", "const", "continue",
            "do", "else", "enum", "false", "true", "final", "finally", "for", "if", "import",
            "new", "null", "return", "switch", "this", "throw",
            "try", "void", "while", "companion", "object", "fun", "override", "val", "var","super"
        )
    }
    //kotlin 注释

    override fun containsKeywords(word: String): Boolean {
        return kKeywords.contains(word)
    }
    /**
     * 注释
     */

}