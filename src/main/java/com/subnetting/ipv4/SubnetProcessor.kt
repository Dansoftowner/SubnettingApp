package com.subnetting.ipv4

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

}