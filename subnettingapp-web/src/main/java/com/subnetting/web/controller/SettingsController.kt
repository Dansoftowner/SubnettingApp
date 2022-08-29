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

package com.subnetting.web.controller

import com.subnetting.web.settings.AppSettings
import com.subnetting.web.settings.ConfigEntry
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.annotation.RequestScope
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestScope
@RequestMapping("/settings")
class SettingsController(private val appSettings: AppSettings) {

    @GetMapping
    fun page(): String {
        return SETTINGS_PAGE
    }

    @PostMapping
    fun saveSettings(request: HttpServletRequest, response: HttpServletResponse): String {
        appSettings.keys.forEach { config ->
            getParameterValue(request, config)?.let { value ->
                appSettings[config.name] = value
                response.addCookie(Cookie(config.name, value))
            }
        }
        return SETTINGS_PAGE
    }

    private fun getParameterValue(request: HttpServletRequest, configEntry: ConfigEntry): String? {
        return request.getParameter(configEntry.name) ?: "false".takeIf { configEntry.type == "checkbox" }
    }

    companion object {
        private const val SETTINGS_PAGE = "settings"
    }

}