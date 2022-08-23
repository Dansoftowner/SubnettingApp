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

/**
 * Used for calculating some values of the subnet represented by the given [ipAddress].
 *
 * @param ipAddress the ip address representing the subnet (it can be a host address too)
 */
class IPV4Subnet(private val ipAddress: IPV4Address) {

    /**
     * Gives the subnet address, if the base [ipAddress] is a host address
     */
    val subnetAddress: IPV4Address by lazy {
        IPV4Address(
            ipAddress.ipValue.and((ipAddress.mask.asNumber())),
            ipAddress.mask
        )
    }

    /**
     * Gives the ip address of the first host
     */
    val firstHostAddress: IPV4Address by lazy {
        IPV4Address(
            subnetAddress.ipValue + 1,
            subnetAddress.mask
        )
    }

    /**
     * Gives the ip address of the last host
     */
    val lastHostAddress: IPV4Address by lazy {
        IPV4Address(
            broadcastAddress.ipValue -1,
            broadcastAddress.mask
        )
    }

    /**
     * Gives the broadcast address
     */
    val broadcastAddress: IPV4Address by lazy {
        IPV4Address(
            subnetAddress.ipValue.or((subnetAddress.mask.asNumber().inv())),
            subnetAddress.mask
        )
    }
}