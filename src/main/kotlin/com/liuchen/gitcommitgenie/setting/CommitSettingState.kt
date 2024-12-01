package com.liuchen.gitcommitgenie.setting

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.liuchen.gitcommitgenie.CommitSettingConfig

@State(name = "org.intellij.sdk.settings.AppSettingsState", storages = [Storage("SdkSettingsPlugin.xml")])
class CommitSettingState : PersistentStateComponent<CommitSettingState> {

    private var requestPath: String? = null
    private var apiKey: String? = null
    private var prompt: String? = null
    private var model: String? = null
    override fun getState(): CommitSettingState {
        return this
    }


    override fun loadState(state: CommitSettingState) {
        XmlSerializerUtil.copyBean(state, this)
    }


    fun isModified(config: CommitSettingConfig): Boolean {
        var modified = config.apiKey != apiKey
        modified = modified or (config.prompt == prompt)
        modified = modified or (config.model == model)
        modified = modified or config.requestPath.equals(requestPath)
        return modified
    }

    fun storeConfig(config: CommitSettingConfig) {
        requestPath = config.requestPath
        apiKey = config.apiKey
        prompt = config.prompt
        model = config.model
    }

    fun getConfig(): CommitSettingConfig {
        return CommitSettingConfig.buildFromCommitSettingState(this)
    }

    fun getRequestPath(): String? {
        return requestPath
    }

    fun getApiKey(): String? {
        return apiKey
    }

    fun getPrompt(): String? {
        return prompt
    }

    fun getModel(): String? {
        return model
    }

    companion object {
        fun getInstance(): CommitSettingState {
            return ApplicationManager.getApplication().getService(CommitSettingState::class.java)
        }

        fun getConfig(): CommitSettingConfig {
            return getInstance().getConfig()
        }
    }
}