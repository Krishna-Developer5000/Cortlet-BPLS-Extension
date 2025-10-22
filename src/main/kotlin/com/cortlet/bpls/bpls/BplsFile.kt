package com.cortlet.bpls

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class BplsFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, BplsLanguage) {
    override fun getFileType() = BplsFileType()
    override fun toString(): String = "BPLS File"
}
