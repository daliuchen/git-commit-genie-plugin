package com.liuchen.gitcommitgenie;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.CommitMessageI;
import com.liuchen.gitcommitgenie.command.GitLogQuery;
import enumation.ChangeType;

import javax.swing.*;
import java.io.File;
import java.util.Enumeration;
import java.util.Objects;

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
    private JComboBox<String> changeScope;
    private JTextField shortDescription;
    private JTextArea longDescription;
    private JButton aigcButton;

    private ButtonGroup changeTypeGroup; // 变更类型

    private Project project; // 项目目录


    CommitPanel(Project project, CommitMessageI commitMessage) {
        this.project = project;
        File workingDirectory = new File(project.getBasePath());
        var logRes = new GitLogQuery(workingDirectory).execute();
        if (!logRes.isEmpty()) {
            changeScope.addItem("");
            logRes.forEach(changeScope::addItem);
        }
        CommitMessage message = parseExistingCommitMessage(commitMessage);
        if (Objects.nonNull(message)) {
            restoreValuesFromParsedCommitMessage(message);
        }
    }


    private CommitMessage parseExistingCommitMessage(CommitMessageI commitMessage) {
        if (commitMessage instanceof CheckinProjectPanel) {
            String commitMessageString = ((CheckinProjectPanel) commitMessage).getCommitMessage();
            return CommitMessage.parse(commitMessageString);
        }
        return null;
    }

    public void restoreValuesFromParsedCommitMessage(CommitMessage commitMessage) {
        if (Objects.nonNull(commitMessage.getChangeType())) {
            for (Enumeration<AbstractButton> buttons = changeTypeGroup.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();

                if (button.getActionCommand().equalsIgnoreCase(commitMessage.getChangeType().label())) {
                    button.setSelected(true);
                }
            }
        }
        changeScope.setSelectedItem(commitMessage.getChangeScope());
        shortDescription.setText(commitMessage.getShortDescription());
        longDescription.setText(commitMessage.getLongDescription());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CommitMessage getCommitMessage() {
        return new CommitMessage(
                getSelectedChangeType(),
                (String) changeScope.getSelectedItem(),
                shortDescription.getText().trim(),
                longDescription.getText().trim()
        );
    }

    private ChangeType getSelectedChangeType() {
        for (Enumeration<AbstractButton> buttons = changeTypeGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return ChangeType.valueOf(button.getActionCommand().toUpperCase());
            }
        }
        return null;
    }
}
