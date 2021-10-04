@file:JvmName("CLIApp")

package com.subnetting.cli

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet

fun main() {
    println("-".repeat(50))
    println("${"-".repeat(18)}Subnetting App${"-".repeat(18)}")
    println("-".repeat(50))

    println("Specify the ip address with the subnet mask!")

    val (ip, mask) = readLine()!!.split(" ")

    val subnet = IPV4Subnet(IPV4Address(ip, IPV4Mask(mask)))
    println("Subnet address: ${subnet.subnetAddress}")
    println("First host address: ${subnet.firstHostAddress}")
    println("Last host address: ${subnet.lastHostAddress}")
    println("Broadcast address: ${subnet.broadcastAddress}")
}