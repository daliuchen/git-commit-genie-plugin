package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "org.intellij.sdk.settings.AppSettingsState", storages = [Storage("SdkSettingsPlugin.xml")])
class CommitSettingState : PersistentStateComponent<CommitSettingState> {

    private val aiAPIKey: String? = null
    private val prompt: String? = null

    private val selectedModelIndex: Int? = null

    private val linearKey: String? = null

    override fun getState(): CommitSettingState {
        return this

    }

    override fun loadState(state: CommitSettingState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val modelList = listOf("GPT-2", "GPT-3", "T5", "BART", "GPT-Neo")

        fun getInstance(): CommitSettingState {
            return ApplicationManager.getApplication().getService(CommitSettingState::class.java)
        }
    }
}