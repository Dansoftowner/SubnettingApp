package com.subnetting.ipv4

import kotlin.math.pow

/**
 * Used for calculating some values of the subnet represented by the given [ipAddress].
 *
 * @param ipAddress the ip address representing the subnet (it can be a host address too)
 */
class IPV4Subnet(private val ipAddress: IPV4Address) {

    /**
     * Gives the subnet address, if the base [ipAddress] is a host address
     */
    val subnetAddress: IPV4Address by lazy {
        IPV4Address(
            ipAddress.firstOctet.and(ipAddress.mask.getMaskForOctet(0)),
            ipAddress.secondOctet.and(ipAddress.mask.getMaskForOctet(1)),
            ipAddress.thirdOctet.and(ipAddress.mask.getMaskForOctet(2)),
            ipAddress.fourthOctet.and(ipAddress.mask.getMaskForOctet(3)),
            ipAddress.mask
        )
    }

    /**
     * Gives the ip address of the first host
     */
    val firstHostAddress: IPV4Address by lazy {
        IPV4Address(
            subnetAddress.firstOctet,
            subnetAddress.secondOctet,
            subnetAddress.thirdOctet,
            subnetAddress.fourthOctet.plus(1u).toUByte(),
            subnetAddress.mask
        )
    }

    /**
     * Gives the ip address of the last host
     */
    val lastHostAddress: IPV4Address by lazy {
        IPV4Address(
            broadcastAddress.firstOctet,
            broadcastAddress.secondOctet,
            broadcastAddress.thirdOctet,
            broadcastAddress.fourthOctet.minus(1u).toUByte(),
            broadcastAddress.mask
        )
    }

    /**
     * Gives the broadcast address
     */
    val broadcastAddress: IPV4Address by lazy {
        // TODO: find a more efficient way to calculate the broadcast address
        val newOctets = subnetAddress.octets.mapIndexed { index, it ->
            val maskForOctet = subnetAddress.mask.getMaskForOctet(index)
            val maskOneBits = maskForOctet.countOneBits()
            if (maskOneBits == 8) {
                it
            } else {
                var binaryString = it.toString(2)
                binaryString = "0".repeat(8 - binaryString.length) + binaryString
                val filledBinaryString = binaryString.replaceRange(
                    maskOneBits,
                    binaryString.length,
                    "1".repeat(binaryString.length - maskOneBits)
                )
                filledBinaryString.toUByte(2)
            }
        }
        IPV4Address(
            newOctets[0],
            newOctets[1],
            newOctets[2],
            newOctets[3],
            subnetAddress.mask
        )
    }

    /**
     * Gives the mask for the particular ipv4 address octet
     *
     * @param octetIndex the index of the octet between 0 and 3
     */
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