package com.subnetting.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SubnettingWebApplication

fun main(args: Array<String>) {
    runApplication<SubnettingWebApplication>(*args)
}