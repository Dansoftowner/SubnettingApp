package com.subnetting.web.settings

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import javax.servlet.http.HttpServletRequest

@Component
@RequestScope
class AppSettings(request: HttpServletRequest) : MutableMap<String, String> by HashMap() {

    init {
        request.cookies
            ?.filter { configurableProperties.contains(it.name) }
            ?.associate { it.name to it.value }
            ?.let(::putAll)
    }

    companion object {
        val configurableProperties = listOf(
            "theme"
        )
    }
}