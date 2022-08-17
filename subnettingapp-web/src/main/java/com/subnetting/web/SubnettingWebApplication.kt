package com.subnetting.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SubnettingWebApplication

fun main(args: Array<String>) {
    runApplication<SubnettingWebApplication>(*args)
}