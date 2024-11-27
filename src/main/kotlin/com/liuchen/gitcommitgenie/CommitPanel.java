package com.liuchen.gitcommitgenie;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CommitMessageI;

import javax.swing.*;

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
    private JButton aigc;


    CommitPanel(Project project, CommitMessageI commitMessage) {

    }


    JPanel getMainPanel() {
        return mainPanel;
    }


}
