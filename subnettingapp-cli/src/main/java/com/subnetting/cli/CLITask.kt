package com.subnetting.cli

interface CLITask {
    val name: String

    fun execute(ip: String, mask: String)
}