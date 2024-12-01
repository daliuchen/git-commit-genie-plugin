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