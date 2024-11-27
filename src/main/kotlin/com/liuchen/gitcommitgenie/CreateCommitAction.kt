package com.liuchen.gitcommitgenie

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable


class CreateCommitAction : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val commitPanel: CommitMessageI = getCommitPanel(e) ?: return
        println("commitPanel: $commitPanel")
        val dialog = CommitDialog(e.project, commitPanel)
        dialog.show()

    }

    fun getCommitPanel(e: AnActionEvent): CommitMessageI? {
        val data = Refreshable.PANEL_KEY.getData(e.dataContext)
        if (data is CommitMessageI) {
            return data
        }
        return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.dataContext)
    }

}