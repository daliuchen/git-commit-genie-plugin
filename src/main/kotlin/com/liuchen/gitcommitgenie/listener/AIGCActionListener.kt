package com.liuchen.gitcommitgenie.listener

import Notifier
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.liuchen.gitcommitgenie.CommitPanel
import com.liuchen.gitcommitgenie.CommitSettingConfig
import com.liuchen.gitcommitgenie.llm.CommitMessageHandler
import com.liuchen.gitcommitgenie.setting.CommitSettingState
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.JButton

class AIGCActionListener(
        private var project: Project,
        private var workingDirectory: File,
        private var commitPanel: CommitPanel,
        private var aigcButton: JButton
) : ActionListener {

    inner class WorkTask : Task.Backgroundable(project, "AI generate commit message") {
        override fun run(indicator: com.intellij.openapi.progress.ProgressIndicator) {
            aigcButton.setEnabled(false)
            val config = CommitSettingState.getConfig()
            if (!checkConfig(config)) {
                aigcButton.setEnabled(true)
                return
            }
            CommitMessageHandler(project, workingDirectory, config).generateCommitMessage()?.let {
                if (!it.isValidCommitMessage()) {
                    commitPanel.setOriginalMessage(it.getOriginCommitMessage())
                    Notifier.notifyWarning(project, "AI generate commit message format invalid")
                } else {
                    commitPanel.restoreValuesFromParsedCommitMessage(it)
                    Notifier.notifyInfo(project, "AI generate commit message success")
                }
            }
            aigcButton.setEnabled(true)
        }
    }


    override fun actionPerformed(e: ActionEvent?) {
        if (!aigcButton.isEnabled) {
            return
        }
        ProgressManager.getInstance().run(WorkTask())
    }

    private fun checkConfig(config: CommitSettingConfig): Boolean {
        if (config.apiKey.isNullOrEmpty()) {
            Notifier.notifyError(project, "API Key is empty, please set it in setting")
            return false
        }
        if (config.prompt.isNullOrEmpty()) {
            Notifier.notifyError(project, "Prompt is empty, please set it in setting")
            return false
        }
        if (config.model.isNullOrEmpty()) {
            Notifier.notifyError(project, "Model is empty, please set it in setting")
            return false
        }
        return true
    }
}