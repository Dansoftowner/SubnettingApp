package com.subnetting.ipv4

import kotlin.math.pow

class IPV4SubnetPartitioner(val baseIp: IPV4Address, val hostCounts: List<Int>) {

    val subnets: List<IPV4Subnet> = requiredMasks.map {
        IPV4Subnet(IPV4Address(baseIp.octets, it))
    }

    private val requiredMasks: List<IPV4Mask>
        get() = hostCounts.map { IPV4Mask(32.minus(it.findAppropriatePower()).toInt()) }

    private fun Int.findAppropriatePower(): Long {
        // TODO: make this algorithm more efficient: O(log n) instead of O(n)
        var power = 0.0
        var i = 0.0
        while (power.minus(2).minus(this) < 0)
            power = 2.0.pow(i++)
        return (i -1).toLong()
    }

    private fun IPV4Address(octets: List<UByte>, mask: IPV4Mask): IPV4Address {
        return IPV4Address(octets[0], octets[1], octets[2], octets[3], mask)
    }

}