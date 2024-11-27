package com.liuchen.gitcommitgenie.command

import java.io.File

class GitBranchNameQuery(workingDirectory: File) : GitBaseQuery<String>(workingDirectory) {
    override val command: String
        get() = "git branch --show-current --format=%s"

    override val parser: CommandResultParser<*>
        get() = object : CommandResultParser<String> {
            override fun parse(output: List<String?>?): String {
                return output?.get(0) ?: ""
            }
        }
}