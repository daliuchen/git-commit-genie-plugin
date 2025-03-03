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

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.liuchen.gitcommitgenie.CommitSettingConfig

@State(name = "com.liuchen.gitcommitgenie.setting.CommitSettingState", storages = [Storage("GitCommitGenie.xml")])
class CommitSettingState : PersistentStateComponent<CommitSettingState> {

    var requestPath: String? = null
    var apiKey: String? = null
    var prompt: String? = null
    var model: String? = null
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

    companion object {
        fun getInstance(): CommitSettingState {
            return ApplicationManager.getApplication().getService(CommitSettingState::class.java)
        }

        fun getConfig(): CommitSettingConfig {
            return getInstance().getConfig()
        }
    }
}