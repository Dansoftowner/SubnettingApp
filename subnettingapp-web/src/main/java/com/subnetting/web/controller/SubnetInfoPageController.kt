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
@RequestMapping("/simpleSubnetInfo")
class SubnetInfoPageController {

    @Autowired
    private lateinit var service: SubnetInfoService

    @RequestMapping
    fun showSubnetInfoPage(model: Model, @RequestParam ip: String, @RequestParam mask: String): String {
        model["entries"] = listOf(service.getEntry(ip, mask))
        return "results"
    }
}
