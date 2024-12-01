package com.liuchen.gitcommitgenie.enumation

import com.alibaba.dashscope.aigc.generation.Generation

enum class ModelType(val model: String) {
    QWEN_TURBO(Generation.Models.QWEN_TURBO),
    QWEN_PLUS(Generation.Models.QWEN_PLUS),
    QWEN_MAX(Generation.Models.QWEN_MAX);


    override fun toString(): String {
        return model
    }
    companion object {
        var MODEL_LISTS = ModelType.entries.map { it.model }.toTypedArray()
    }

}