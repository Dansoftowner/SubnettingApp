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
     * The Int representation of the IPv4 address.
     *
     * Since it's an Int that uses 4 bytes,
     * it's perfectly capable to store a 32-bit Ipv4 address.
     */
    val intValue: Int,

    /**
     * The object representing the corresponding subnet mask.
     */
    val mask: IPV4Mask
) {

    /**
     * Constructs the ipv4 address from the given string and mask
     *
     * @param stringFormat the string representing the ip address in the dot-decimal format
     *                     it should not contain the subnet mask
     * @param mask the subnet mask representation
     */
    constructor(stringFormat: String, mask: IPV4Mask) : this(toIpIntValue(stringFormat), mask)

    /**
     * Converts the IPv4 address into a string with this format: "x.x.x.x /mask".
     */
    override fun toString(): String = toString(ToStringOption.ADDRESS_AND_MASK)

    /**
     * Converts the IPv4 address to a string based on the given conditions.
     *
     * @param format the [IPV4Address.ToStringOption] representing the desired format
     * @param maskFormat only has a role if the [format] is [IPV4Address.ToStringOption.ADDRESS_AND_MASK];
     * it's [IPV4Mask.ToStringOption.CIDR_NOTATION] by default.
     */
    fun toString(
        format: ToStringOption,
        maskFormat: IPV4Mask.ToStringOption = IPV4Mask.ToStringOption.CIDR_NOTATION
    ): String = format.toString(this, maskFormat)

    companion object {
        private fun toIpIntValue(ipString: String): Int {
            require(ipString.matches(Regex(IPV4_ADDRESS_PATTERN)))
            var ipNumber = 0
            ipString.split(".").map { it.toInt() }.forEachIndexed { i, octet ->
                ipNumber = ipNumber.or(octet.shl((3 - i) * 8))
            }
            return ipNumber
        }
    }

    enum class ToStringOption {
        /**
         * Can be used to convert the [IPV4Address] to an "x.x.x.x" string
         *
         * @see [IPV4Address.toString]
         */
        ONLY_ADDRESS {
            override fun toString(ipV4Address: IPV4Address, ignored: IPV4Mask.ToStringOption): String {
                val stringBuilder = StringBuilder()
                for (i in 3 downTo  0) {
                    val octet = (ipV4Address.intValue.and(255.shl(i * 8))).ushr(i * 8)
                    stringBuilder.append(octet)
                    stringBuilder.append(if (i != 0) "." else "")
                }
                return stringBuilder.toString()
            }
        },

        /**
         * Can be used to convert the [IPV4Address] to an "x.x.x.x {mask}" string
         *
         * @see IPV4Address.toString
         */
        ADDRESS_AND_MASK {
            override fun toString(ipV4Address: IPV4Address, maskFormat: IPV4Mask.ToStringOption): String {
                return ONLY_ADDRESS.toString(ipV4Address, maskFormat) + " ${ipV4Address.mask.toString(maskFormat)}"
            }
        };

        abstract fun toString(ipV4Address: IPV4Address, maskFormat: IPV4Mask.ToStringOption): String
    }
}