package com.cortlet.bpls

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

class BplsCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet
                ) {
                    // 💥 Auto-suggestions for BPLS keywords
                    val keywords = listOf("PRINT", "SET", "INPUT")

                    for (keyword in keywords) {
                        resultSet.addElement(
                            LookupElementBuilder.create(keyword)
                                .withBoldness(true)
                                .withTypeText("BPLS keyword", true)
                                .withTailText("()", true)
                        )
                    }
                }
            }
        )
    }
}
