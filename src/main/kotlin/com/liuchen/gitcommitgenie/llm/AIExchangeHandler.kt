package com.liuchen.gitcommitgenie.llm

import com.liuchen.gitcommitgenie.CommitSettingConfig

interface AIExchangeHandler {
    fun exchange(config: CommitSettingConfig, input: String): String
}