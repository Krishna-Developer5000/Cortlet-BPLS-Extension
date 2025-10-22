package com.cortlet.bpls

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class BplsFileType : LanguageFileType(BplsLanguage) {
    override fun getName() = "BPLS File"
    override fun getDescription() = "BPLS source file"
    override fun getDefaultExtension() = "bpls"
    override fun getIcon(): Icon? = IconLoader.getIcon("/icons/bpls.svg", javaClass)
}
