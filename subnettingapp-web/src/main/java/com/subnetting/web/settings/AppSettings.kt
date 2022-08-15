package com.subnetting.web.settings

import com.subnetting.web.settings.ConfigEntry.Option
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import javax.servlet.http.HttpServletRequest

@Component
@SessionScope
class AppSettings(request: HttpServletRequest) : MutableMap<ConfigEntry, Option> by HashMap() {

    init {
        putEntries()
        println("AppSettings object created!")
        request.cookies
            ?.filter { configurableProperties.contains(it.name) }
            ?.associate { it.name to it.value }
            ?.mapKeys { it -> toConfigEntry(it.key)!! }
            ?.mapValues { entry -> entry.key.toOption(entry.value)!! }
            ?.forEach { this[it.key] = it.value }

    }

    private fun putEntries() {
        // TODO: hardcoded

        configList.forEach {
            this[it] = it.defaultValue
        }
    }

    operator fun get(entryName: String) = this[toConfigEntry(entryName)]

    operator fun set(entryName: String, optionName: String) {
        val configEntry = toConfigEntry(entryName)!!
        set(configEntry, configEntry.toOption(optionName)!!)
    }

    companion object {
        // TODO: should be read from config file
        val themeConfiguration = ConfigEntry(
            name = "theme",
            options = listOf(
                Option("dark"),
                Option("light"),
                Option("auto")
            ),
            defaultValue = Option("dark")
        )

        val configList = listOf(themeConfiguration)

        val configurableProperties: List<String> by lazy { configList.map { it.name } }

        fun toConfigEntry(name: String) = configList.find { it.name == name }
    }
}