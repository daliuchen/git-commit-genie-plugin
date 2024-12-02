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

package com.liuchen.gitcommitgenie.llm.model

import com.alibaba.dashscope.aigc.generation.Generation
import com.alibaba.dashscope.aigc.generation.GenerationParam
import com.alibaba.dashscope.common.Message
import com.alibaba.dashscope.common.Role
import com.alibaba.dashscope.utils.Constants
import com.liuchen.gitcommitgenie.CommitSettingConfig
import com.liuchen.gitcommitgenie.llm.AIExchangeHandler

//@Service(Service.Level.APP)
class QianWenModelHandler : AIExchangeHandler {
    override fun exchange(config: CommitSettingConfig, input: String): String {
        val gen = Generation()
        Constants.apiKey = config.apiKey!!

        if (!config.requestPath.isNullOrEmpty()) {
            Constants.baseHttpApiUrl = config.requestPath
        }
        val messages: MutableList<Message> = ArrayList()
        val userMsg = Message.builder().role(Role.USER.value).content(input).build()
        messages.add(userMsg)
        val param = GenerationParam.builder().model(config.model!!).messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build()
        val result = gen.call(param)
        val choices = result.output.choices
        return choices.firstOrNull()?.message?.content ?: ""
    }
}