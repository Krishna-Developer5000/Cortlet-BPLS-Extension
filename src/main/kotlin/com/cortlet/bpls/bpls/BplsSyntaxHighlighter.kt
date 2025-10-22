package com.cortlet.bpls

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType
import com.intellij.lexer.Lexer
import java.awt.Color

class BplsSyntaxHighlighter : SyntaxHighlighter {
    // Light + vibrant but readable colors 🎨
    private val PRINT = TextAttributesKey.createTextAttributesKey(
        "BPLS_PRINT", TextAttributes(Color(70, 130, 180), null, null, null, 2)
    ) // Steel Blue
    private val SET = TextAttributesKey.createTextAttributesKey(
        "BPLS_SET", TextAttributes(Color(186, 85, 211), null, null, null, 1)
    ) // Medium Orchid
    private val INPUT = TextAttributesKey.createTextAttributesKey(
        "BPLS_INPUT", TextAttributes(Color(255, 165, 0), null, null, null, 0)
    ) // Orange
    private val BAD = TextAttributesKey.createTextAttributesKey(
        "BPLS_BAD", TextAttributes(Color(255, 99, 71), null, null, null, 1)
    ) // Tomato Red

    override fun getHighlightingLexer(): Lexer = BplsFakeLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType.toString().uppercase()) {
            "PRINT" -> arrayOf(PRINT)
            "SET" -> arrayOf(SET)
            "INPUT" -> arrayOf(INPUT)
            "BAD" -> arrayOf(BAD)
            else -> emptyArray()
        }
    }
}
