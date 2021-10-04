@file:JvmName("CLIApp")

package com.subnetting.cli

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.SubnetProcessor

fun main() {
    println("-".repeat(50))
    println("${"-".repeat(18)}Subnetting App${"-".repeat(18)}")
    println("-".repeat(50))

    println("Specify the ip address with the subnet mask!")

    val (ip, mask) = readLine()!!.split(" ")

    val subnetProcessor = SubnetProcessor(IPV4Address(ip, IPV4Mask(mask)))
    println("Subnet address: ${subnetProcessor.subnetAddress}")
    println("First host address: ${subnetProcessor.firstHostAddress}")
    println("Last host address: ${subnetProcessor.lastHostAddress}")
    println("Broadcast address: ${subnetProcessor.broadcastAddress}")
}