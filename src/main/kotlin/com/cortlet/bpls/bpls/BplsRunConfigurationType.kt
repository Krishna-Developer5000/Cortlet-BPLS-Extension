package com.cortlet.bpls

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.icons.AllIcons
import javax.swing.Icon

class BplsRunConfigurationType : ConfigurationType {

    override fun getDisplayName() = "BPLS Interpreter"
    override fun getConfigurationTypeDescription() = "Runs BPLS source files"
    override fun getIcon(): Icon = AllIcons.RunConfigurations.Application
    override fun getId() = "BPLS_RUN_CONFIGURATION" // ✅ non-localized stable ID

    override fun getConfigurationFactories(): Array<ConfigurationFactory> =
        arrayOf(BplsConfigurationFactory(this))
}

class BplsConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {

    override fun createTemplateConfiguration(project: com.intellij.openapi.project.Project) =
        BplsRunConfiguration(project, this, "BPLS Interpreter")

    override fun getId(): String = "BPLS_RUN_FACTORY" // ✅ this removes all PluginExceptions

    override fun getName(): String = "BPLS Interpreter Factory"
}
