package com.cortlet.bpls

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import javax.swing.JPanel

class BplsRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<RunProfileState>(project, factory, name) {

    var filePath: String? = null

    override fun getConfigurationEditor(): SettingsEditor<out BplsRunConfiguration> {
        return object : SettingsEditor<BplsRunConfiguration>() {
            override fun resetEditorFrom(s: BplsRunConfiguration) {}
            override fun applyEditorTo(s: BplsRunConfiguration) {}
            override fun createEditor() = JPanel()
        }
    }

    override fun checkConfiguration() {
        // No validation needed yet, but you could check file existence here
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return object : CommandLineState(environment) {
            override fun startProcess(): OSProcessHandler {
                val dataContext: DataContext? = environment.dataContext
                val file: VirtualFile? = dataContext?.getData(CommonDataKeys.VIRTUAL_FILE)
                val path = file?.path ?: filePath ?: throw RuntimeException("No .bpls file selected")

                val cmd = listOf("bpls", path)
                val processBuilder = ProcessBuilder(cmd)
                val process = processBuilder.start()
                val handler = OSProcessHandler(process, cmd.joinToString(" "))
                ProcessTerminatedListener.attach(handler)
                return handler
            }
        }
    }
}
