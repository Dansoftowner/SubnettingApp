package com.subnetting.ipv4

import kotlin.math.pow

class IPV4SubnetPartitioner(baseIp: IPV4Address, val hostCounts: List<Int>) {

    private val baseIp = IPV4Subnet(baseIp).subnetAddress

    val subnets: List<IPV4Subnet>
        get() {
            var lastSubnet: IPV4Subnet? = null
            return requiredMasks.map { mask ->
                when (lastSubnet) {
                    null -> IPV4Subnet(IPV4Address(this.baseIp.octets, mask))
                    else -> IPV4Subnet(IPV4Address(lastSubnet!!.broadcastAddress.next().octets, mask))
                }.also { lastSubnet = it }
            }
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

    private fun IPV4Address.next(): IPV4Address {
        val octetStrings = stringFormat.split(".").toMutableList()
        for (i in octetStrings.size.minus(1) downTo 0) {
            val item = octetStrings[i].toInt()
            if (item < 255) {
                octetStrings[i] = item.plus(1).toString()
                for (j in (i + 1) until 4) {
                    octetStrings[j] = "0"
                }
                break
            }
        }

        return IPV4Address(octetStrings.map(String::toUByte), mask).also {
            println("The next ip to '${this.stringFormat}' is '${it.stringFormat}")
        }
    }

}