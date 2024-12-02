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

package com.liuchen.gitcommitgenie.command

import java.io.File
import java.util.regex.Pattern


class GitLogQuery(workingDirectory: File) : GitBaseQuery<Set<String>>(workingDirectory) {
    companion object {
        private const val GIT_LOG_COMMAND = "git log --all --format=%s"
        private val COMMIT_FIRST_LINE_FORMAT = Pattern.compile("^[a-z]+\\((.+)\\):.*")
    }

    override val command: String
        get() = GIT_LOG_COMMAND
    override val parser: CommandResultParser<Set<String>>
        get() = object : CommandResultParser<Set<String>> {
            override fun parse(output: List<String?>?): Set<String> {
                val scopes = mutableSetOf<String>()
                if (output == null) {
                    return scopes
                }
                for (s in output) {
                    val matcher = COMMIT_FIRST_LINE_FORMAT.matcher(s)
                    if (matcher.find()) {
                        scopes.add(matcher.group(1))
                    }
                }
                return scopes
            }
        }
}