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