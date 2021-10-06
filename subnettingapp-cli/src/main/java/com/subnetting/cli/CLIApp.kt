@file:JvmName("CLIApp")

package com.subnetting.cli

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet
import com.subnetting.ipv4.IPV4SubnetPartitioner

fun main() {
    println("-".repeat(50))
    println("${"-".repeat(18)}Subnetting App${"-".repeat(18)}")
    println("-".repeat(50))

    println("Specify the ip address with the subnet mask!")

    val (ip, mask) = readLine()!!.split(" ")

    println("What do you want to do?")
    println("")

}

private fun printBasicSubnetInfo(ip: String, mask: String) {
    printBasicSubnetInfo(IPV4Address(ip, IPV4Mask(mask)))
}

private fun printBasicSubnetInfo(ip: IPV4Address) {
    printBasicSubnetInfo(IPV4Subnet(ip))
}

private fun printBasicSubnetInfo(subnet: IPV4Subnet) {
    println("Subnet address: ${subnet.subnetAddress}")
    println("First host address: ${subnet.firstHostAddress}")
    println("Last host address: ${subnet.lastHostAddress}")
    println("Broadcast address: ${subnet.broadcastAddress}")
}

private fun printSubnetPartitions(ip: String, mask: String, hostCounts: List<Int>) {
    printSubnetPartitions(IPV4Address(ip, IPV4Mask(mask)), hostCounts)
}

private fun printSubnetPartitions(ip: IPV4Address, hostCounts: List<Int>) {
    val partitioner = IPV4SubnetPartitioner(ip, hostCounts)
    partitioner.subnets.forEach {
        println("-".repeat(10))
        printBasicSubnetInfo(it)
        println()
    }
}