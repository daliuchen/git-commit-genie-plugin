package com.liuchen.gitcommitgenie

import com.liuchen.gitcommitgenie.enumation.ChangeType
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.WordUtils
import java.util.*
import java.util.regex.Pattern


class CommitMessage(
        private var changeType: ChangeType?,
        private var changeScope: String?,
        private var shortDescription: String,
        private var longDescription: String?,
        private var originCommitMessage: String?
) {
    constructor() : this(null, null, "", "", null)
    constructor(changeType: ChangeType?,
                changeScope: String?,
                shortDescription: String,
                longDescription: String) : this(changeType, changeScope, shortDescription, longDescription, null)

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(changeType?.label() ?: ChangeType.FEAT.label())
        if (StringUtils.isNotBlank(changeScope)) {
            builder.append('(')
                    .append(changeScope)
                    .append(')')
        }
        builder.append(": ")
                .append(shortDescription)
        if (StringUtils.isNotBlank(longDescription)) {
            builder.append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(WordUtils.wrap(longDescription, MAX_LINE_LENGTH))
        }
        return builder.toString()
    }

    fun getChangeType(): ChangeType? {
        return changeType
    }

    fun getChangeScope(): String? {
        return changeScope
    }

    fun getShortDescription(): String {
        return shortDescription
    }

    fun getLongDescription(): String? {
        return longDescription
    }

    fun setShortDescription(shortDescription: String) {
        this.shortDescription = shortDescription
    }

    fun isValidCommitMessage(): Boolean {
        return changeType != null && StringUtils.isNotBlank(shortDescription)
    }

    fun setChangeType(changeType: ChangeType?) {
        this.changeType = changeType
    }

    fun setChangeScope(changeScope: String?) {
        this.changeScope = changeScope
    }

    fun setLongDescription(longDescription: String) {
        this.longDescription = longDescription
    }

    fun getOriginCommitMessage(): String? {
        return originCommitMessage

    }

    fun setOriginCommitMessage(originCommitMessage: String) {
        this.originCommitMessage = originCommitMessage

    }

    fun isValid(): Boolean {
        return changeType != null && StringUtils.isNotBlank(shortDescription)
    }

    companion object {
        private const val MAX_LINE_LENGTH = 72 // https://stackoverflow.com/a/2120040/5138796
        val COMMIT_FIRST_LINE_FORMAT = Pattern.compile("^([a-z]+)(\\((.+)\\))?: (.+)")


        @JvmStatic
        fun parse(message: String): CommitMessage {
            val commitMessage = CommitMessage()
            try {
                val matcher = COMMIT_FIRST_LINE_FORMAT.matcher(message)
                matcher.takeIf { it.find() }?.let {
                    commitMessage.changeType = ChangeType.valueOf(it.group(1).uppercase(Locale.getDefault()))
                    commitMessage.changeScope = it.group(3)
                    commitMessage.shortDescription = it.group(4)
                }

                message.split("\n").also { strings ->
                    if (strings.size > 1) {
                        commitMessage.longDescription = strings.drop(1).joinToString("\n").trim()
                    }
                }
            } catch (ignored: Exception) {
            }
            return commitMessage
        }
    }
}