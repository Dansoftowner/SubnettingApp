package com.subnetting.web.controller

import com.subnetting.web.settings.AppSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/settings")
class SettingsController {

    @Autowired
    lateinit var appContext: ApplicationContext

    @GetMapping
    fun page(): String {
        return "settings"
    }


    @PostMapping
    fun saveSettings(request: HttpServletRequest, response: HttpServletResponse): String {
        val appSettings = appContext.getBean(AppSettings::class.java)
        AppSettings.configurableProperties.forEach {
            request.getParameter(it)?.let { value ->
                appSettings[it] = value
                response.addCookie(Cookie(it, value))
            }
        }
        return page()
    }

}