package com.liuchen.gitcommitgenie.command

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

abstract class GitBaseQuery<T> protected constructor(private val workingDirectory: File) {
    interface CommandResultParser<T> {
        fun parse(output: List<String?>?): T
    }

    abstract val command: String
    abstract val parser: CommandResultParser<*>

    fun execute(): T? {
        try {
            val command = command
            val processBuilder: ProcessBuilder
            val osName = System.getProperty("os.name")
            processBuilder = if (osName.contains("Windows")) {
                ProcessBuilder("cmd", "/C", command)
            } else {
                ProcessBuilder("sh", "-c", command)
            }
            val process = processBuilder
                    .directory(workingDirectory)
                    .start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = reader.lines().collect(Collectors.toList())
            process.waitFor(2, TimeUnit.SECONDS)
            process.destroy()
            if (process.exitValue() != 0) {
                return null
            }
            if (process.exitValue() != 0) {
                return null
            }
            return parser.parse(output) as T
        } catch (ignored: Exception) {
        }
        return null
    }
}