package com.subnetting.ipv4

import kotlin.math.pow

class SubnetProcessor(val ipAddress: IPV4Address) {

    val subnetAddress: IPV4Address by lazy {
        IPV4Address(
            ipAddress.firstOctet.and(ipAddress.mask.getMaskForOctet(0)),
            ipAddress.secondOctet.and(ipAddress.mask.getMaskForOctet(1)),
            ipAddress.thirdOctet.and(ipAddress.mask.getMaskForOctet(2)),
            ipAddress.fourthOctet.and(ipAddress.mask.getMaskForOctet(3)),
            ipAddress.mask
        )
    }

    val firstHostAddress: IPV4Address by lazy {
        IPV4Address(
            subnetAddress.firstOctet,
            subnetAddress.secondOctet,
            subnetAddress.thirdOctet,
            subnetAddress.fourthOctet.plus(1u).toUByte(),
            subnetAddress.mask
        )
    }

    val lastHostAddress: IPV4Address
        get() = TODO()

    val broadcastAddress: IPV4Address
        get() = TODO()

    private fun IPV4Mask.getMaskForOctet(octetIndex: Int): UByte {
        val fullOctetsCount = bitCount / 8
        return if (octetIndex < fullOctetsCount) {
            255u
        } else if (octetIndex == fullOctetsCount) {
            var remainedBits = bitCount - (fullOctetsCount * 8)
            var mask = 0
            var i = 8
            while (remainedBits > 0) {
                mask += 2.0.pow(--i).toInt()
                remainedBits--
            }
            mask.toUByte()
        } else {
            0u
        }

        // bitCount = 25 -> 11111111 11111111 11111111 10000000
        // fullOctetsCount = 25 / 8 -> 3,125 -> 3
        // remainedBits = 25 - (3 * 8) = 1
    }
}