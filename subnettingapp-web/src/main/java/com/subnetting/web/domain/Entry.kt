package com.subnetting.web.domain

class Entry(
    val subnetAddress: String,
    val firstHostAddress: String,
    val lastHostAddress: String,
    val broadcastAddress: String,
    val subnetMask: String
)