package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN
import com.subnetting.MASK_CIDR_PATTERN
import kotlin.math.pow

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
            stringFormat.matches(Regex(MASK_CIDR_PATTERN)) -> {
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

    /**
     * Gives the mask-octet by the given octet-index.
     *
     * @param octetIndex the index of the octet between 0 and 3
     */
    operator fun get(octetIndex: Int): UByte {
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

    override fun toString(): String {
        return toString(ToStringOption.CIDR_NOTATION)
    }

    fun toString(option: ToStringOption): String {
        return when(option) {
            ToStringOption.CIDR_NOTATION -> "/$bitCount"
            ToStringOption.DOT_DECIMAL_NOTATION -> "${this[0]}.${this[1]}.${this[2]}.${this[3]}"
        }
    }

    enum class ToStringOption {
        CIDR_NOTATION,
        DOT_DECIMAL_NOTATION
    }

}