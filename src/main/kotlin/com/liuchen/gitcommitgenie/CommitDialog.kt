package com.liuchen.gitcommitgenie

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vcs.CommitMessageI
import javax.swing.JComponent


class CommitDialog internal constructor(project: Project?, commitMessage: CommitMessageI?) : DialogWrapper(project) {
    private val panel: CommitPanel

    init {
        panel = CommitPanel(project, commitMessage)
        title = "Commit"
        setOKButtonText("OK")
        panel.setDialog(this)
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return panel.mainPanel
    }


    fun getCommitMessage(): String {
        return panel.commitMessage.toString()
    }

}

