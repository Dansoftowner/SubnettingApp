/*
 * SubnettingApp
 * Copyright (c) 2022.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.subnetting.web.settings

import com.subnetting.web.settings.ConfigEntry.Option
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import javax.servlet.http.HttpServletRequest

@Component
@RequestScope
class AppSettings(request: HttpServletRequest, private val configEntries: List<ConfigEntry>) : MutableMap<ConfigEntry, Option> by HashMap() {

    private val List<ConfigEntry>.configNames get() = map { it.name }

    init {
        putEntries()
        readFromCookies(request)
    }

    private fun putEntries() {
        configEntries.forEach {
            this[it] = it.defaultValue
        }
    }

    private fun readFromCookies(request: HttpServletRequest) {
        request.cookies
            ?.filter { configEntries.configNames.contains(it.name) }
            ?.associate { it.name to it.value }
            ?.mapKeys { it -> configEntries.toConfigEntry(it.key) }
            ?.mapValues { entry -> entry.key?.toOption(entry.value) }
            ?.filter { it.key != null && it.value != null }
            ?.forEach { this[it.key!!] = it.value!! }
    }

    operator fun get(entryName: String) = this[configEntries.toConfigEntry(entryName)]

    operator fun set(entryName: String, optionName: String) {
        val configEntry = configEntries.toConfigEntry(entryName)!!
        set(configEntry, configEntry.toOption(optionName)!!)
    }

    private fun List<ConfigEntry>.toConfigEntry(entryName: String) = find { it.name == entryName }
}