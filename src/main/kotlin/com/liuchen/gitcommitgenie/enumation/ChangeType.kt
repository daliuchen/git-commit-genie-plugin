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

import java.util.*

enum class ChangeType(private val title: String, private val description: String) {
    FEAT("Features", "A new feature"),
    FIX("Bug Fixes", "A bug fix"),
    DOCS("Documentation", "Documentation only changes"),
    STYLE("Styles", "Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)"),
    REFACTOR("Code Refactoring", "A code change that neither fixes a bug nor adds a feature"),
    PERF("Performance Improvements", "A code change that improves performance"),
    TEST("Tests", "Adding missing tests or correcting existing tests"),
    BUILD("Builds", "Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)"),
    CI("Continuous Integrations", "Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)"),
    CHORE("Chores", "Other changes that don't modify src or test files"),
    REVERT("Reverts", "Reverts a previous commit");

    fun label(): String {
        return name.lowercase(Locale.getDefault())
    }

    override fun toString(): String {
        return String.format("%s - %s", label(), description)
    }

    companion object {
        fun fromLabel(label: String): ChangeType? {
            return values().find { it.label() == label }
        }
    }
}