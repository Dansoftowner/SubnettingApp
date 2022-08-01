package com.subnetting.web.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/options")
class OptionsPageController {

    @GetMapping
    fun showOptionsPage(model: Model, @RequestParam ip: String, @RequestParam mask: String): String {
        model["ip"] = ip
        model["mask"] = mask
        return "options.html"
    }
}