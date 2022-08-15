package com.subnetting.web.settings

open class ConfigEntry(val name: String, val localizedName: String = name, val options: List<Option>, val defaultValue: Option) {

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

    class Option(val name: String, val localizedName: String = name) {

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
}