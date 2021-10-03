package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN
import com.subnetting.MASK_ROUTING_PREFIX_PATTERN
import kotlin.math.pow

class IPV4Mask(value: String) {

    val bitCount: Int

    init {
        bitCount = when {
            value.matches(Regex(MASK_ROUTING_PREFIX_PATTERN)) -> {
                val numberValue = value.substring(1).toInt()
                require(numberValue <= 32) // TODO: ask
                numberValue
            }
            value.matches(Regex(IPV4_ADDRESS_PATTERN)) -> {
                val octets = value.split(".").map { it.toUByte() }
                octets.sumOf { it.countOneBits() }
            }
            else -> throw IllegalArgumentException()
        }
    }

    fun getMaskForOctet(octetIndex: Int): UByte {
        val fullOctetsCount = bitCount / 8
        return if (octetIndex < fullOctetsCount) {
            255u
        } else {
            var remainedBits = bitCount - (fullOctetsCount * 8)
            var mask = 0
            var i = 8
            while (remainedBits > 0) {
                mask += 2.0.pow(--i).toInt()
                remainedBits--
            }
            mask.toUByte()
        }

        // bitCount = 25 -> 11111111 11111111 11111111 10000000
        // fullOctetsCount = 25 / 8 -> 3,125 -> 3
        // remainedBits = 25 - (3 * 8) = 1
    }
}