/*
 * Copyright 2023 liuchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuchen.gitcommitgenie;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.CommitMessageI;
import com.liuchen.gitcommitgenie.command.GitLogQuery;
import com.liuchen.gitcommitgenie.enumation.ChangeType;
import com.liuchen.gitcommitgenie.listener.AIGCActionListener;

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

    private ButtonGroup changeTypeGroup;

    private Project project;
    private File workingDirectory;
    private CommitMessageI commitMessage;

    private CommitDialog commitDialog;



    CommitPanel(Project project, CommitMessageI commitMessage) {
        this.project = project;
        this.commitMessage = commitMessage;
        File workingDirectory = new File(project.getBasePath());
        this.workingDirectory = workingDirectory;

        var logRes = new GitLogQuery(workingDirectory).execute();
        if (!logRes.isEmpty()) {
            changeScope.addItem("");
            logRes.forEach(changeScope::addItem);
        }
        CommitMessage message = parseExistingCommitMessage(commitMessage);
        if (Objects.nonNull(message)) {
            restoreValuesFromParsedCommitMessage(message);
        }

        initActions();
    }

    private void initActions() {
        aigcButton.addActionListener(new AIGCActionListener(project, workingDirectory, this, aigcButton));
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

    public CommitMessage getCommitMessage() {
        return new CommitMessage(
                getSelectedChangeType(),
                (String) changeScope.getSelectedItem(),
                shortDescription.getText().trim(),
                longDescription.getText().trim()
        );
    }

    public void setOriginalMessage(String commitMessage) {
        // DialogWrapper.close must be called on the Event Dispatch Thread (EDT). You can use SwingUtilities.invokeLater to ensure that the code runs on the EDT.
        SwingUtilities.invokeLater(() -> {
            commitDialog.close(DialogWrapper.CANCEL_EXIT_CODE);
            this.commitMessage.setCommitMessage(commitMessage);
        });
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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setDialog(CommitDialog commitDialog) {
        this.commitDialog = commitDialog;
    }
}
