package com.liuchen.gitcommitgenie.listener

import com.intellij.openapi.project.Project
import com.jetbrains.rd.util.string.printToString
import com.liuchen.gitcommitgenie.CommitPanel
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

    private inner class WorkerThread : Thread() {
        override fun run() {
            println("AIGCActionListener:")
            aigcButton.setEnabled(false)
            println(project.printToString())
            println(workingDirectory.printToString())
            println(commitPanel.printToString())
            sleep(5000)
            aigcButton.setEnabled(true)
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        WorkerThread().start()
    }
}