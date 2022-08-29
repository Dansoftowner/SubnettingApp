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

package com.subnetting.web.i18n

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * This interceptor prevents language-configuration difficulties.
 *
 * 1. It makes possible to specify the language through a query-param (the parameter name is 'language').
 *    > This feature also provides fluent-configuration for the user from the `settings` view: when the language
 *      is just configured, the server will not parse the locale from the cookie (because that's the previous configuration)
 *      but it will read the newly configured value.
 *
 * 2. It does not allow to store a cookie for the language if it's automatic
 */
@Component
class LanguageHandlingInterceptor(private val applicationContext: ApplicationContext) : LocaleChangeInterceptor() {

    init {
        paramName = "language"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (isLanguageAuto(request)) {
            // if the newly configured value is auto then we read the locale from the Accept-Language header
            applicationContext.getBean("localeResolver", LocaleResolver::class.java)
                .setLocale(request, response, request.locale)
            return true
        }
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        // If language is automatic, we shouldn't store a cookie for it
        if (isLanguageAuto(request)) {
            response.addCookie(Cookie("language", "auto").apply { maxAge = 0 })
        }
    }

    private fun isLanguageAuto(request: HttpServletRequest): Boolean {
        return request.getParameter("language") == "auto"
    }
}