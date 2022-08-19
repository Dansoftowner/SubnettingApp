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

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/error")
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping
    fun handleError(model: Model, request: HttpServletRequest, response: HttpServletResponse): String {
        model.addAttribute("status", response.status)
        model.addAttribute("path", request.path)
        return when {
            response.is404() -> "error/404"
            request.isFromResultsPage() -> "error/form"
            else -> "error/general"
        }
    }

    private val HttpServletRequest.path get() = getAttribute(RequestDispatcher.ERROR_REQUEST_URI).toString()

    private fun HttpServletRequest.isFromResultsPage() = path.contains("/results")

    private fun HttpServletResponse.is404() =
        status == 404
}
