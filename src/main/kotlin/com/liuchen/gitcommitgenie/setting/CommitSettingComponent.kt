package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.liuchen.gitcommitgenie.enumation.ModelType
import javax.swing.DefaultComboBoxModel
import javax.swing.JPanel

class CommitSettingsComponent {
    private var settingPanel: JPanel? = null

    private var requestPathField: JBTextField = JBTextField()
    private var apiKeyField: JBTextField = JBTextField()
    private var promptField: JBTextArea = JBTextArea(10, 10)
    private var modelField: ComboBox<String> = ComboBox()

    init {
        initUi()
        val openAiPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("RequestPath"), requestPathField, 1, false)
                .addLabeledComponent(JBLabel("ApiKey"), apiKeyField, 1, false)
                .addLabeledComponent(JBLabel("Prompt"), JBScrollPane(promptField), 1, false)
                .addLabeledComponent(JBLabel("Model"), modelField, 1, false)
                .panel


        settingPanel = FormBuilder.createFormBuilder()
                .addComponent(openAiPanel, 0)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    private fun initUi() {
        modelField.setModel(DefaultComboBoxModel(ModelType.MODEL_LISTS))
    }

    fun getSettingPanel(): JPanel? {
        return settingPanel
    }
}