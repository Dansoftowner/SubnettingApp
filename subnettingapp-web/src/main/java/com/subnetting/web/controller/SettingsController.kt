package com.subnetting.web.controller

import com.subnetting.web.settings.AppSettings
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
        return "settings"
    }


    @PostMapping
    fun saveSettings(request: HttpServletRequest, response: HttpServletResponse): String {
        appSettings.keys.map { it.name }.forEach {
            request.getParameter(it)?.let { value ->
                appSettings[it] = value
                response.addCookie(Cookie(it, value))
            }
        }
        return page()
    }

}