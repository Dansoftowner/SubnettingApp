package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN

class IPV4Address {

    val stringFormat: String
    val mask: IPV4Mask

    val firstOctet: UByte
    val secondOctet: UByte
    val thirdOctet: UByte
    val fourthOctet: UByte

    val octets: List<UByte> get() = listOf(firstOctet, secondOctet, thirdOctet, fourthOctet)

    constructor(octet1: UByte, octet2: UByte, octet3: UByte, octet4: UByte, mask: IPV4Mask) {
        this.stringFormat = "$octet1.$octet2.$octet3.$octet4"
        this.mask = mask
        this.firstOctet = octet1
        this.secondOctet = octet2
        this.thirdOctet = octet3
        this.fourthOctet = octet4
    }

    constructor(stringFormat: String, mask: IPV4Mask) {
        require(stringFormat.matches(Regex(IPV4_ADDRESS_PATTERN)))
        this.stringFormat = stringFormat
        this.mask = mask
        val octets = stringFormat
            .split(".")
            .map { it.toUByte() }
        firstOctet = octets[0]
        secondOctet = octets[1]
        thirdOctet = octets[2]
        fourthOctet = octets[3]
    }

    override fun toString(): String {
        return "$firstOctet.$secondOctet.$thirdOctet.$fourthOctet /${mask.bitCount}"
    }
}