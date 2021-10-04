package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN

/**
 * Represents an IPV4 address.
 *
 * @author Daniel Gyoerffy
 */
class IPV4Address {

    /**
     * The dot separated format of the ip address
     */
    val stringFormat: String

    /**
     * The representation of the subnet mask
     */
    val mask: IPV4Mask

    /**
     * The first 8-bit octet
     */
    val firstOctet: UByte

    /**
     * The second 8 bit octet
     */
    val secondOctet: UByte

    /**
     * The third 8 bit octet
     */
    val thirdOctet: UByte

    /**
     * The fourth 8 bit octet
     */
    val fourthOctet: UByte

    /**
     * The list of the 4 octets
     */
    val octets: List<UByte>
        get() = listOf(firstOctet, secondOctet, thirdOctet, fourthOctet)

    /**
     * Constructs the ipv4 address from the four given octet and subnet mask.
     *
     * @param octet1 the first octet
     * @param octet2 the second octet
     * @param octet3 the third octet
     * @param octet4 the fourth octet
     */
    constructor(
        octet1: UByte,
        octet2: UByte,
        octet3: UByte,
        octet4: UByte,
        mask: IPV4Mask
    ) {
        this.stringFormat = "$octet1.$octet2.$octet3.$octet4"
        this.mask = mask
        this.firstOctet = octet1
        this.secondOctet = octet2
        this.thirdOctet = octet3
        this.fourthOctet = octet4
    }

    /**
     * Constructs the ipv4 address from the given string and mask
     *
     * @param stringFormat the string representing the ip address in the dot-decimal format
     *                     it should not contain the subnet mask
     * @param mask the subnet mask representation
     */
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