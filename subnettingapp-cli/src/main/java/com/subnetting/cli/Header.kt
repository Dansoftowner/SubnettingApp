package com.subnetting.cli

class Header {
    override fun toString(): String {
        val prefixSuffix = "-".repeat(50)
        val title = "${"-".repeat(18)} Subnetting App ${"-".repeat(18)}"
        return "$prefixSuffix\n$title\n$prefixSuffix"
    }
}