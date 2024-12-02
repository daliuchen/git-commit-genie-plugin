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