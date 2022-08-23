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

/**
 * Represents an IPV4 address.
 *
 * @author Daniel Gyoerffy
 */
class IPV4Address(
    /**
     * The number that actually stores the IP address.
     *
     * Since it's an Int that uses 4 bytes,
     * it's perfectly capable to store a 32-bit Ipv4 address.
     */
    val ipValue: Int,

    /**
     * The object representing the corresponding subnet mask.
     */
    val mask: IPV4Mask,

    stringFormat: String? = null
) {

    /**
     * The dot separated format of the ip address
     */
    val stringFormat: String =
        stringFormat ?: ToStringOption.ONLY_ADDRESS.toString(this, IPV4Mask.ToStringOption.CIDR_NOTATION)

    /**
     * Constructs the ipv4 address from the given string and mask
     *
     * @param stringFormat the string representing the ip address in the dot-decimal format
     *                     it should not contain the subnet mask
     * @param mask the subnet mask representation
     */
    constructor(stringFormat: String, mask: IPV4Mask) : this(toIpNumber(stringFormat), mask, stringFormat)

    override fun toString(): String = toString(ToStringOption.ADDRESS_AND_MASK)

    fun toString(
        option: ToStringOption,
        maskFormat: IPV4Mask.ToStringOption = IPV4Mask.ToStringOption.CIDR_NOTATION
    ): String = option.toString(this, maskFormat)

    companion object {
        fun toIpNumber(stringFormat: String): Int {
            require(stringFormat.matches(Regex(IPV4_ADDRESS_PATTERN)))
            var ipNumber = 0
            stringFormat.split(".").map { it.toInt() }.forEachIndexed { i, octet ->
                ipNumber = ipNumber.or(octet.shl((3 - i) * 8))
            }
            return ipNumber
        }
    }

    enum class ToStringOption {
        ONLY_ADDRESS {
            override fun toString(ipV4Address: IPV4Address, ignored: IPV4Mask.ToStringOption): String {
                val stringBuilder = StringBuilder()
                for (i in 3 downTo  0) {
                    val octet = (ipV4Address.ipValue.and(255.shl(i * 8))).ushr(i * 8)
                    stringBuilder.append(octet)
                    stringBuilder.append(if (i != 0) "." else "")
                }
                return stringBuilder.toString()
            }
        },
        ADDRESS_AND_MASK {
            override fun toString(ipV4Address: IPV4Address, maskFormat: IPV4Mask.ToStringOption): String {
                return ONLY_ADDRESS.toString(ipV4Address, maskFormat) + " ${ipV4Address.mask.toString(maskFormat)}"
            }
        };

        abstract fun toString(ipV4Address: IPV4Address, maskFormat: IPV4Mask.ToStringOption): String
    }
}