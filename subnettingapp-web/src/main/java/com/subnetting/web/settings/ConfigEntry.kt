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

data class ConfigEntry @JvmOverloads constructor(
    val name: String = "",
    val localizedName: String = name,
    val group: Group? = null,
    val type: String = "select",
    val options: List<Option> = emptyList(),
    val defaultValue: Option = Option()
) {

    override fun toString(): String {
        return name
    }

    fun toOption(optionName: String) = options.find { it.name == optionName }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfigEntry

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    class Option @JvmOverloads constructor(
        val name: String = "",
        val localizedName: String = name
    ) {

        override fun toString(): String {
            return name
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Option

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    class Group @JvmOverloads constructor(
        val name: String = "",
        val localizedName: String = name
    ) {
        override fun toString(): String {
            return name
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Group

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }


    }
}