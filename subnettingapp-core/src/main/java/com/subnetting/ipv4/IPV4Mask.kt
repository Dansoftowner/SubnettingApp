/*
 * SubnettingApp
 * Copyright (c) 2022.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.subnetting.ipv4

import com.subnetting.IPV4_ADDRESS_PATTERN
import com.subnetting.MASK_CIDR_PATTERN
import kotlin.math.pow

/**
 * Represents an IPV4 subnet mask. Supports only continuous masks!
 *
 * @author Daniel Gyoerffy
 */
class IPV4Mask(val bitCount: Int) {

    /**
     * The 32-bit Int representation of the subnet mask.
     *
     * > e.g. IPV4Mask(bitCount = 8).asInt -> 0xFF000000
     */
    val asInt: Int by lazy {
        val num = 1.shl(bitCount -1)
        num.or(num - 1).shl(32 - bitCount)
    }

    init {
        require(bitCount in 1 until 32)
    }

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
                require(numberValue in 1..32) // TODO: ask
                numberValue
            }
            stringFormat.matches(Regex(IPV4_ADDRESS_PATTERN)) -> {
                val octets = stringFormat.split(".").map { it.toUByte() }.also {
                    it.chunked(2).forEach { (val1, val2) -> require(val1 >= val2) } // the mask must be continuous
                }
                octets.sumOf { it.countOneBits() }.also { require(it > 0) }
            }
            else -> throw IllegalArgumentException()
        }
    )

    /**
     * Gives the mask-octet by the given octet-index.
     *
     * @param octetIndex the index of the octet between 0 and 3
     */
    operator fun get(octetIndex: Int): Int {
        return (asInt.shr(24 - octetIndex * 8)).and(255)
    }

    override fun toString(): String {
        return toString(ToStringOption.CIDR_NOTATION)
    }

    fun toString(option: ToStringOption): String {
        return option.toString(bitCount)
    }

    enum class ToStringOption {
        CIDR_NOTATION {
            override fun toString(bitCount: Int) = "/$bitCount"
        },
        DOT_DECIMAL_NOTATION {
            override fun toString(bitCount: Int): String {
                val stringBuilder = StringBuilder()
                var bc = bitCount
                for (i in 1..4) {
                    var octet = 0
                    for (k in 7 downTo 0) {
                        if (bc == 0) break
                        octet = octet.or(1.shl(k))
                        bc--
                    }
                    stringBuilder.append(octet)
                    stringBuilder.append(if (i != 4) "." else "")
                }
                return stringBuilder.toString()
            }
        };

        abstract fun toString(bitCount: Int): String
    }

}