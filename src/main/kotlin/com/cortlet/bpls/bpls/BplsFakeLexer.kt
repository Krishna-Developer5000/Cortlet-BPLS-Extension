package com.cortlet.bpls

import com.intellij.lexer.LexerBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class BplsFakeLexer : LexerBase() {
    private var buffer: CharSequence = ""
    private var startOffset = 0
    private var endOffset = 0
    private var bufferEnd = 0
    private var tokenType: IElementType? = null

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = startOffset
        this.bufferEnd = endOffset
        this.tokenType = null
        advance()
    }

    override fun getState(): Int = 0
    override fun getTokenType(): IElementType? = tokenType
    override fun getTokenStart(): Int = startOffset
    override fun getTokenEnd(): Int = endOffset
    override fun getBufferSequence(): CharSequence = buffer
    override fun getBufferEnd(): Int = bufferEnd

    override fun advance() {
        if (endOffset >= bufferEnd) {
            tokenType = null
            return
        }

        startOffset = endOffset
        val c = buffer[startOffset]

        // Handle whitespace
        if (c.isWhitespace()) {
            var i = startOffset
            while (i < bufferEnd && buffer[i].isWhitespace()) i++
            endOffset = i
            tokenType = TokenType.WHITE_SPACE
            return
        }

        // Capture a word
        var i = startOffset
        while (i < bufferEnd && !buffer[i].isWhitespace()) i++

        val word = buffer.subSequence(startOffset, i).toString()
        val upper = word.uppercase()
        endOffset = i

        tokenType = when (upper) {
            "PRINT" -> BplsTokenType("PRINT")
            "SET" -> BplsTokenType("SET")
            "INPUT" -> BplsTokenType("INPUT")
            else -> BplsTokenType("IDENTIFIER") // ✅ Not garbage anymore!
        }
    }
}

class BplsTokenType(private val debugName: String) : IElementType(debugName, BplsLanguage) {
    override fun toString(): String = debugName
}
