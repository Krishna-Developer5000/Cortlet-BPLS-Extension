
package com.cortlet.bpls

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import kotlin.math.min

class BplsAnnotator : Annotator {
    private val keywords = listOf("PRINT", "SET", "INPUT")

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // 🧹 Ignore large blocks or structured nodes
        if (element.firstChild != null) return

        val text = element.text.trim()
        if (text.isEmpty()) return

        // 🧠 Ignore anything that's not a-z or A-Z (numbers, punctuation, etc.)
        if (!text.matches(Regex("^[A-Za-z]+\$"))) return

        // 🪄 Detect if this is the FIRST token in the line (potential command)
        val parentText = element.containingFile.text
        val lineStart = parentText.lastIndexOf('\n', element.textOffset - 1).let { if (it == -1) 0 else it + 1 }
        val beforeText = parentText.substring(lineStart, element.textOffset).trim()

        // 🧠 If something exists before it, it’s not the command → skip annotation
        if (beforeText.isNotEmpty()) return

        val upper = text.uppercase()
        val exactMatch = keywords.firstOrNull { text == it }
        val caseMismatch = keywords.firstOrNull { it.equals(text, ignoreCase = true) && text != it }

        if (exactMatch != null) return // ✅ Valid command, no annotation needed

        // 🚫 Case-sensitive error
        if (caseMismatch != null) {
            holder.newAnnotation(
                HighlightSeverity.ERROR,
                "BPLS is case-sensitive — use '$caseMismatch'"
            )
                .range(element.textRange)
                .gutterIconRenderer(BplsErrorGutter(text, "Use '$caseMismatch'"))
                .create()
            return
        }

        // 🔍 Check for typo suggestions
        val closest = keywords.minByOrNull { levenshteinDistance(it, upper) }
        val distance = closest?.let { levenshteinDistance(it, upper) } ?: Int.MAX_VALUE

        if (distance <= 2 && closest != null) {
            holder.newAnnotation(
                HighlightSeverity.ERROR,
                "Unknown Command: '$text'. Did you mean '$closest'?"
            )
                .range(element.textRange)
                .gutterIconRenderer(BplsErrorGutter(text, "Did you mean '$closest'?"))
                .create()
        } else {
            holder.newAnnotation(
                HighlightSeverity.ERROR,
                "Unknown Command: '$text'"
            )
                .range(element.textRange)
                .gutterIconRenderer(BplsErrorGutter(text, "Unknown Command"))
                .create()
        }
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        val dp = Array(a.length + 1) { IntArray(b.length + 1) }
        for (i in 0..a.length) dp[i][0] = i
        for (j in 0..b.length) dp[0][j] = j
        for (i in 1..a.length) {
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                dp[i][j] = min(
                    min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[a.length][b.length]
    }
}
