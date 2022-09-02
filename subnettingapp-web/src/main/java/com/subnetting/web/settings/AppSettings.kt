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

/**
 * Bean that stores the application configurations for a given user.
 *
 * A new instance is created by every request, and it reads the configurations from the user's cookies.
 * It's basically a processed form of the cookies.
 */
@Component
@RequestScope
class AppSettings(
    request: HttpServletRequest,
    private val configEntries: List<ConfigEntry>
) : MutableMap<ConfigEntry, Option> by LinkedHashMap() {

    private val List<ConfigEntry>.configNames get() = map { it.name }

    init {
        putEntries()
        readFromCookies(request)
    }

    /**
     * Puts the default values
     */
    private fun putEntries() {
        configEntries.forEach {
            this[it] = it.defaultValue
        }
    }

    /**
     * Parses the configurations from the sent-in cookies
     */
    private fun readFromCookies(request: HttpServletRequest) {
        request.cookies
            ?.filter { configEntries.configNames.contains(it.name) }
            ?.associate { it.name to it.value }
            ?.mapKeys { it -> configEntries.toConfigEntry(it.key) }
            ?.mapValues { entry -> entry.key?.toOption(entry.value) }
            ?.filter { it.key != null && it.value != null }
            ?.forEach { this[it.key!!] = it.value!! }
    }

    /**
     * Gets the configured value for the given key.
     *
     * @param entryName the string identifier of the configuration
     */
    operator fun get(entryName: String): Option? = this[configEntries.toConfigEntry(entryName)]

    /**
     * Maps the given config-value to the configuration.
     *
     * @param entryName the name of the configuration
     * @param optionName the name of the option
     */
    operator fun set(entryName: String, optionName: String) {
        val configEntry = configEntries.toConfigEntry(entryName)!!
        set(configEntry, configEntry.toOption(optionName)!!)
    }

    /**
     * Gets the configurations by their groups.
     */
    fun getEntriesByGroup() : Map<ConfigEntry.Group?, List<ConfigEntry>> = keys.groupBy { it.group }

    /**
     * Finds the config-entry in the list according to the given name
     */
    private fun List<ConfigEntry>.toConfigEntry(entryName: String) = find { it.name == entryName }
}