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

package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.liuchen.gitcommitgenie.CommitSettingConfig
import com.liuchen.gitcommitgenie.enumation.ModelType
import javax.swing.DefaultComboBoxModel
import javax.swing.JPanel

class CommitSettingComponent {
    private var settingPanel: JPanel? = null

    private var requestPathField: JBTextField = JBTextField()
    private var apiKeyField: JBTextField = JBTextField()
    private var promptField: JBTextArea = JBTextArea(10, 10)
    private var modelField: ComboBox<String> = ComboBox()


    init {
        initUi()
        val jbScrollPane = JBScrollPane(promptField)
        val openAiPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("ApiKey *"), apiKeyField, 1, false)
            .addLabeledComponent(JBLabel("Prompt"), jbScrollPane, 1, false)
                .addLabeledComponent(JBLabel("Model *"), modelField, 1, false)
                .addLabeledComponent(JBLabel("RequestPath"), requestPathField, 1, false)
                .panel


        settingPanel = FormBuilder.createFormBuilder()
                .addComponent(openAiPanel, 0)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    private fun initUi() {
        modelField.setModel(DefaultComboBoxModel(ModelType.MODEL_LISTS))

    }


    fun resetValues(config: CommitSettingConfig) {
        requestPathField.text = config.requestPath
        apiKeyField.text = config.apiKey
        promptField.text = config.prompt
        modelField.selectedItem = config.model
        promptField.caretPosition = 0
    }

    fun getSettingPanel(): JPanel? {
        return settingPanel
    }

    fun getRequestPath(): String {
        return requestPathField.text
    }

    fun getApiKey(): String {
        return apiKeyField.text
    }

    fun getPrompt(): String {
        return promptField.text
    }

    fun getModel(): String? {
        return modelField.selectedItem as String?
    }
}
