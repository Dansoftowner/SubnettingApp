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
     * The list of the 4 octets
     */
    val octets: List<UByte>

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
        this.octets = listOf(octet1, octet2, octet3, octet4)
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
        octets = stringFormat
            .split(".")
            .map { it.toUByte() }
    }

    operator fun get(octetIndex: Int): UByte {
        return octets[octetIndex]
    }

    override fun toString(): String {
        return toString(ToStringOption.ADDRESS_AND_MASK)
    }

    fun toString(
        option: ToStringOption,
        maskFormat: IPV4Mask.ToStringOption = IPV4Mask.ToStringOption.CIDR_NOTATION
    ): String =
        when (option) {
            ToStringOption.ONLY_ADDRESS -> "${this[0]}.${this[1]}.${this[2]}.${this[3]}"
            ToStringOption.ADDRESS_AND_MASK -> toString(ToStringOption.ONLY_ADDRESS, maskFormat) + " ${
                mask.toString(
                    maskFormat
                )
            }"
        }

    enum class ToStringOption {
        ONLY_ADDRESS,
        ADDRESS_AND_MASK
    }
}