package com.liuchen.gitcommitgenie;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CommitMessageI;

import javax.swing.*;
import java.io.File;
import java.util.Set;

public class CommitPanel {
    private JPanel mainPanel;
    private JRadioButton featRadioButton;
    private JRadioButton fixRadioButton;
    private JRadioButton docsRadioButton;
    private JRadioButton styleRadioButton;
    private JRadioButton refactorRadioButton;
    private JRadioButton perfRadioButton;
    private JRadioButton testRadioButton;
    private JRadioButton buildRadioButton;
    private JRadioButton ciRadioButton;
    private JRadioButton choreRadioButton;
    private JRadioButton revertRadioButton;
    private JComboBox changeScope;
    private JTextField shortDescription;
    private JTextArea longDescription;
    private JButton aigcButton;

    private ButtonGroup changeTypeGroup; // 变更类型

    private Project project; // 项目目录
    private String linearCardNo; // Linear需求


    CommitPanel(Project project, CommitMessageI commitMessage) {
        File workingDirectory = new File(project.getBasePath());

        Set<String> logRes = new GitLogQuery(workingDirectory).execute();
        String linearCardNo = new LinearCardNoService().getLinearCardNo(workingDirectory);

        if (!logRes.isEmpty()) {
            changeScope.addItem("");
            logRes.forEach(changeScope::addItem);
        }
        CommitMessage aiCommitMessage = parseExistingCommitMessage(linearCardNo, commitMessage);
        if (aiCommitMessage != null) {
            restoreValuesFromParsedCommitMessage(aiCommitMessage);
        }
        this.project = project;
        this.linearCardNo = linearCardNo;
        initActions();
    }


    JPanel getMainPanel() {
        return mainPanel;
    }


}
