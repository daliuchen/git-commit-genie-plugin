package com.liuchen.gitcommitgenie.enumation

enum class ModelType(val model: String) {
    QWEN_TURBO("qwen-turbo");

    companion object {
        var MODEL_LISTS = arrayOf(QWEN_TURBO.model)
    }

}