package com.subnetting.cli

interface CLITask {
    val name: String

    operator fun invoke(ip: String, mask: String)
}