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
            ?.mapKeys { it -> configEntries.toConfigEntry(it.key)!! }
            ?.mapValues { entry -> entry.key.toOption(entry.value)!! }
            ?.forEach { this[it.key] = it.value }
    }

    operator fun get(entryName: String) = this[configEntries.toConfigEntry(entryName)]

    operator fun set(entryName: String, optionName: String) {
        val configEntry = configEntries.toConfigEntry(entryName)!!
        set(configEntry, configEntry.toOption(optionName)!!)
    }

    private fun List<ConfigEntry>.toConfigEntry(entryName: String) = find { it.name == entryName }
}