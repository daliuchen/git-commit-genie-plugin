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

package com.liuchen.gitcommitgenie.llm

import Notifier
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.liuchen.gitcommitgenie.CommitMessage
import com.liuchen.gitcommitgenie.CommitSettingConfig
import com.liuchen.gitcommitgenie.command.GitDiffQuery
import com.liuchen.gitcommitgenie.enumation.ChangeType
import com.liuchen.gitcommitgenie.enumation.ModelType
import com.liuchen.gitcommitgenie.llm.model.QianWenModelHandler
import org.apache.commons.lang.StringUtils
import java.io.File
import java.util.*


class CommitMessageHandler(
        private var project: Project,
        private var workingDirectory: File,
        private var config: CommitSettingConfig
) {
    private var handlerMapping = mutableMapOf<String, AIExchangeHandler>()
    private val logger: Logger = Logger.getInstance(CommitMessageHandler::class.java)


    init {
        handlerMapping[ModelType.QWEN_TURBO.toString()] = QianWenModelHandler()
        handlerMapping[ModelType.QWEN_PLUS.toString()] = QianWenModelHandler()
        handlerMapping[ModelType.QWEN_MAX.toString()] = QianWenModelHandler()
    }

    fun generateCommitMessage(): CommitMessage? {
        val model = config.model
        val handler = handlerMapping[model]
        if (Objects.isNull(handler)) {
            return null
        }
        val input = buildInput()
        if (input.isBlank()) {
            return null
        }
        val commitMessageStr = handler!!.exchange(config, input)
        return transform(commitMessageStr)
    }

    private fun buildInput(): String {
        var diffChanges = GitDiffQuery(workingDirectory).execute()
        if (diffChanges.isNullOrBlank()) {
            Notifier.notifyError(project, "No changes found in git diff")
            return ""
        }

        diffChanges = diffChanges.replace("No newline at end of file", "")
        return config.prompt!!.replace("{diff}", diffChanges)
    }


    private fun transform(param: String): CommitMessage {
        val res = CommitMessage()
        res.setOriginCommitMessage(param)

        var response = StringUtils.removeStart(param, "=").trim { it <= ' ' }
        response = StringUtils.removeStart(response, "```").trim { it <= ' ' }
        response = StringUtils.removeStartIgnoreCase(response, "json").trim { it <= ' ' }
        response = StringUtils.removeEndIgnoreCase(response, "json").trim { it <= ' ' }
        response = StringUtils.removeEnd(response, "```").trim { it <= ' ' }
        response = StringUtils.removeEnd(response, "=").trim { it <= ' ' }

        var obj: JSONObject?
        try {
            obj = JSON.parseObject(response)
        } catch (e: Exception) {
            return res
        }

        res.setChangeType(ChangeType.fromLabel(obj.getString("type")))
        res.setChangeScope(obj.getString("scope"))
        res.setShortDescription(obj.getString("short_description"))
        val longerDescription: String? = obj.getString("longer_description")
        if (!longerDescription.isNullOrBlank()) {
            try {

                val ds = JSONArray.parseArray(longerDescription, String::class.java)
                res.setLongDescription(ds.joinToString(System.lineSeparator()))
            } catch (_: Exception) {
                res.setLongDescription(longerDescription)
            }
        }
        return res
    }
}