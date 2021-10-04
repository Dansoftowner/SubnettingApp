package com.subnetting.ipv4

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
            ipAddress[0].and(ipAddress.mask[0]),
            ipAddress[1].and(ipAddress.mask[1]),
            ipAddress[2].and(ipAddress.mask[2]),
            ipAddress[3].and(ipAddress.mask[3]),
            ipAddress.mask
        )
    }

    /**
     * Gives the ip address of the first host
     */
    val firstHostAddress: IPV4Address by lazy {
        IPV4Address(
            subnetAddress[0],
            subnetAddress[1],
            subnetAddress[2],
            subnetAddress[3].plus(1u).toUByte(),
            subnetAddress.mask
        )
    }

    /**
     * Gives the ip address of the last host
     */
    val lastHostAddress: IPV4Address by lazy {
        IPV4Address(
            broadcastAddress[0],
            broadcastAddress[1],
            broadcastAddress[2],
            broadcastAddress[3].minus(1u).toUByte(),
            broadcastAddress.mask
        )
    }

    /**
     * Gives the broadcast address
     */
    val broadcastAddress: IPV4Address by lazy {
        // TODO: find a more efficient way to calculate the broadcast address
        val newOctets = subnetAddress.octets.mapIndexed { index, it ->
            val maskForOctet = subnetAddress.mask[index]
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
}