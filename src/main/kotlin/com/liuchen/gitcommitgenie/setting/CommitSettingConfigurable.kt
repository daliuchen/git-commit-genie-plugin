package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class CommitSettingConfigurable : Configurable {
    private var settingsComponent: CommitSettingsComponent? = null
    override fun createComponent(): JComponent? {
        settingsComponent = CommitSettingsComponent()
        return settingsComponent!!.getSettingPanel()
    }

    override fun isModified(): Boolean {
        return false

    }

    override fun apply() {

    }

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "AI Commit Template"
    }

}