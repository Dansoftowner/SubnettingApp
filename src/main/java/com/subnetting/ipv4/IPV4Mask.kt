package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN
import com.subnetting.MASK_ROUTING_PREFIX_PATTERN

/**
 * Represents an IPV4 subnet mask.
 *
 * @author Daniel Gyoerffy
 */
class IPV4Mask(val bitCount: Int) {

    /**
     * Constructs the ipv4 mask by the given string representation.
     *
     * The string can follow the _CIDR_ notation (like `/24`)
     * or the _dot decimal notation_ (like `255.255.255.0`)
     */
    constructor(stringFormat: String) : this(
        when {
            stringFormat.matches(Regex(MASK_ROUTING_PREFIX_PATTERN)) -> {
                val numberValue = stringFormat.substring(1).toInt()
                require(numberValue <= 32) // TODO: ask
                numberValue
            }
            stringFormat.matches(Regex(IPV4_ADDRESS_PATTERN)) -> {
                val octets = stringFormat.split(".").map { it.toUByte() }
                octets.sumOf { it.countOneBits() }
            }
            else -> throw IllegalArgumentException()
        }
    )
}