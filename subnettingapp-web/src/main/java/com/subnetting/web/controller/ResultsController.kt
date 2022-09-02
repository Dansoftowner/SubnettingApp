package com.subnetting.web.controller

import com.subnetting.web.service.ResultsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/results")
class ResultsController(private val service: ResultsService) {

    @GetMapping
    fun results(
        model: Model,
        @RequestParam ip: String,
        @RequestParam mask: String,
        @RequestParam task: String,
        @RequestParam(required = false) hostCounts: String?
    ): String {
        model["entries"] = service.getEntries(ip, mask, task, hostCounts)
        return "pages/results"
    }
}
