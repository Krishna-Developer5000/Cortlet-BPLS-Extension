package com.cortlet.bpls

import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class BplsErrorGutter(private val text: String, private val tooltip: String) : GutterIconRenderer() {
    private val icon: Icon = IconLoader.getIcon("/icons/gutter.svg", javaClass)

    override fun getIcon(): Icon = icon
    override fun getTooltipText(): String = tooltip
    override fun equals(other: Any?) = other is BplsErrorGutter && other.text == text
    override fun hashCode(): Int = text.hashCode()
}
