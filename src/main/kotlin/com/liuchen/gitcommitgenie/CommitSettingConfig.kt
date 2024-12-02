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
