package com.liuchen.gitcommitgenie.llm

import Notifier
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.intellij.openapi.project.Project
import com.liuchen.gitcommitgenie.CommitMessage
import com.liuchen.gitcommitgenie.CommitSettingConfig
import com.liuchen.gitcommitgenie.command.GitDiffQuery
import com.liuchen.gitcommitgenie.enumation.ChangeType
import com.liuchen.gitcommitgenie.enumation.ModelType
import com.liuchen.gitcommitgenie.llm.model.QianWenModelHandler
import org.apache.commons.lang.StringUtils
import java.io.File

class CommitMessageHandler(
        private var project: Project,
        private var workingDirectory: File,
        private var config: CommitSettingConfig
) {
    private var handlerMapping = mutableMapOf<String, AIExchangeHandler>()

    init {
        handlerMapping[ModelType.QWEN_TURBO.toString()] = QianWenModelHandler()
    }

    fun generateCommitMessage(): CommitMessage? {
        val model = config.model
        val handler = handlerMapping[model]
        val input = buildInput()
        if (input.isBlank()) {
            return null
        }
        val commitMessageStr = handler?.exchange(config, input)
        if (commitMessageStr.isNullOrBlank()) {
            return null
        }
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