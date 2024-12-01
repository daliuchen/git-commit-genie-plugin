package com.liuchen.gitcommitgenie

import com.liuchen.gitcommitgenie.llm.PromptManager
import com.liuchen.gitcommitgenie.setting.CommitSettingComponent
import com.liuchen.gitcommitgenie.setting.CommitSettingState

data class CommitSettingConfig(
        var requestPath: String?,
        var apiKey: String?,
        var prompt: String?,
        var model: String?
) {
    companion object {
        fun buildFromSettingComponent(settingComponent: CommitSettingComponent): CommitSettingConfig {
            return CommitSettingConfig(
                    requestPath = settingComponent.getRequestPath(),
                    apiKey = settingComponent.getApiKey(),
                    prompt = settingComponent.getPrompt(),
                    model = settingComponent.getModel()
            )
        }

        fun buildFromCommitSettingState(settingState: CommitSettingState): CommitSettingConfig {
            val res = CommitSettingConfig(
                    requestPath = settingState.getRequestPath(),
                    apiKey = settingState.getApiKey(),
                    prompt = settingState.getPrompt(),
                    model = settingState.getModel()
            )
            if (res.prompt.isNullOrBlank()) {
                res.prompt = PromptManager.DEFAULT_COMMIT_PROMPT
            }
            return res
        }


    }
}
