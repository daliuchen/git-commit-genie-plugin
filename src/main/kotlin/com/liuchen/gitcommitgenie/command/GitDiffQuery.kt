package com.liuchen.gitcommitgenie.command

import org.apache.commons.lang.StringUtils
import java.io.File

class GitDiffQuery(workingDirectory: File) : GitBaseQuery<String?>(workingDirectory) {
    override val command: String
        get() = "git diff"

    override val parser: CommandResultParser<*>
        get() = object : CommandResultParser<String?> {
            override fun parse(output: List<String?>?): String? {
                if (output.isNullOrEmpty()) {
                    return null
                }
                return StringUtils.join(output, "\n")
            }
        }
}