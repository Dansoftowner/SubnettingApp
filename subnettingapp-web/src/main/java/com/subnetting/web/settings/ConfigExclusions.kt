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

import org.springframework.stereotype.Component

@Component
class ConfigExclusions {

    private val exclusionFunctions: MutableList<(String, String) -> Boolean> = mutableListOf()

    @Synchronized
    fun isExcluded(configName: String, configValue: String): Boolean {
        return exclusionFunctions.find { it(configName, configValue) } != null
    }

    @Synchronized
    fun addExclusion(exclusionFunction: (configName: String, value: String) -> Boolean) {
        exclusionFunctions.add(exclusionFunction)
    }
}