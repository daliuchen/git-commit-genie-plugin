package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.options.Configurable
import com.liuchen.gitcommitgenie.CommitSettingConfig
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class CommitSettingConfigurable : Configurable {
    private var settingsComponent: CommitSettingComponent? = null
    override fun createComponent(): JComponent? {
        settingsComponent = CommitSettingComponent()
        return settingsComponent!!.getSettingPanel()
    }

    override fun isModified(): Boolean {
        val settingsState = CommitSettingState.getInstance()
        val config = CommitSettingConfig.buildFromSettingComponent(settingsComponent!!)
        return settingsState.isModified(config)
    }

    override fun apply() {
        val settingsState = CommitSettingState.getInstance()
        val config = CommitSettingConfig.buildFromSettingComponent(settingsComponent!!)
        settingsState.storeConfig(config)
    }

    override fun reset() {
        val settingsState = CommitSettingState.getInstance()
        val config = CommitSettingConfig.buildFromCommitSettingState(settingsState)
        settingsComponent!!.resetValues(config)
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Git Commit Genie"
    }
}