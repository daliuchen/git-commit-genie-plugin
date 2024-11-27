package com.liuchen.gitcommitgenie

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable


class CreateCommitAction : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val commitPanel: CommitMessageI = getCommitPanel(e) ?: return
        val dialog = CommitDialog(e.project, commitPanel)
        dialog.show()

        if (dialog.exitCode == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(dialog.getCommitMessage())
        }
    }

    fun getCommitPanel(e: AnActionEvent): CommitMessageI? {
        val data = Refreshable.PANEL_KEY.getData(e.dataContext)
        if (data is CommitMessageI) {
            return data
        }
        return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.dataContext)
    }

}