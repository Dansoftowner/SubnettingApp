package com.subnetting.web.domain

/**
 * Represents a "box" that will be shown for the user on the /results page.
 */
class Entry(
    val subnetAddress: String,
    val firstHostAddress: String,
    val lastHostAddress: String,
    val broadcastAddress: String,
    val subnetMask: String
)