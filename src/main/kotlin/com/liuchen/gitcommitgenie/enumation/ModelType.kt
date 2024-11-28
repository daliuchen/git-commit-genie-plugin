package com.liuchen.gitcommitgenie.enumation

enum class ModelType(val model: String) {
    GPT("gpt"),
    GPT2("gpt2"),
    GPT3("gpt3"),
    DAVINCI("davinci"),
    CURIE("curie"),
    BABBAGE("babbage"),
    ADA("ada"),
    UNKNOWN("unknown");

    companion object {
        fun getModelType(model: String): ModelType {
            return when (model) {
                "gpt" -> GPT
                "gpt2" -> GPT2
                "gpt3" -> GPT3
                "davinci" -> DAVINCI
                "curie" -> CURIE
                "babbage" -> BABBAGE
                "ada" -> ADA
                else -> UNKNOWN
            }
        }

        var MODEL_LISTS = arrayOf(GPT.model, GPT2.model, GPT3.model, DAVINCI.model, CURIE.model, BABBAGE.model, ADA.model)
    }

}