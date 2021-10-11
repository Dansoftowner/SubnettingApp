package com.subnetting.cli

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet
import com.subnetting.ipv4.IPV4SubnetPartitioner

class SimpleSubnetInfoTask : CLITask {

    override val name = "Simple subnet info"

    override fun invoke(ip: String, mask: String) {
        printBasicSubnetInfo(ip, mask)
    }
}

class SubnetPartitioningTask : CLITask {

    override val name = "Subnet partitioning"

    override fun invoke(ip: String, mask: String) {
        println("Give the host counts comma separated:")
        val hostCounts = readLine()!!.split(",")
            .map(String::trim)
            .map(String::toInt)
        printSubnetPartitions(ip, mask, hostCounts)
    }
}

private fun printBasicSubnetInfo(ip: String, mask: String) {
    printBasicSubnetInfo(IPV4Address(ip, IPV4Mask(mask)))
}

private fun printBasicSubnetInfo(ip: IPV4Address) {
    printBasicSubnetInfo(IPV4Subnet(ip))
}

private fun printBasicSubnetInfo(subnet: IPV4Subnet) {
    println("Subnet address: ${subnet.subnetAddress}")
    println("First host address: ${subnet.firstHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS)}")
    println("Last host address: ${subnet.lastHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS)}")
    println("Broadcast address: ${subnet.broadcastAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS)}")
    println("Subnet mask: ${subnet.subnetAddress.mask.toString(IPV4Mask.ToStringOption.DOT_DECIMAL_NOTATION)}")
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