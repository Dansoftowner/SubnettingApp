package com.subnetting.web.controller

import com.subnetting.web.service.SubnetInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/results")
class ResultsController {

    @Autowired
    private lateinit var service: SubnetInfoService

    @GetMapping
    fun results(
        model: Model,
        @RequestParam ip: String,
        @RequestParam mask: String,
        @RequestParam task: String,
        @RequestParam hostCounts: String
    ): String {
        model["entries"] = service.getEntries(ip, mask, task, hostCounts)
        return "results"
    }
}
