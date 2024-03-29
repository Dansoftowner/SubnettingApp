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

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver

/**
 * Provides all necessary configurations required for fluent internationalization.
 */
@Configuration
class I18nConfig(private val languageHandlingInterceptor: LanguageHandlingInterceptor) : WebMvcConfigurer {

    @Bean("messageSource")
    fun messageSource(): MessageSource = ResourceBundleMessageSource().apply {
        setBasenames("i18n/messages")
        setDefaultEncoding("UTF-8")
    }

    @Bean
    fun localeResolver(): LocaleResolver = CookieLocaleResolver().apply {
        cookieName = "language"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(languageHandlingInterceptor)
    }

}