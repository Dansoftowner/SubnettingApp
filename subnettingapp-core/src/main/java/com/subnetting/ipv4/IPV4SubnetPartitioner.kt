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

import kotlin.math.ceil
import kotlin.math.log

/**
 * An [IPV4SubnetPartitioner] is capable to divide the IP range to subnets that are capable
 * to hold the number of the given [hostCounts].
 */
class IPV4SubnetPartitioner(baseIp: IPV4Address, private val hostCounts: List<Int>) {

    private val baseIp = IPV4Subnet(baseIp).subnetAddress

    /**
     * The subnets calculated.
     */
    val subnets: List<IPV4Subnet> by lazy {
        var lastSubnet: IPV4Subnet? = null
        requiredMasks.map { mask ->
            when (lastSubnet) {
                null -> IPV4Subnet(IPV4Address(this.baseIp.intValue, mask))
                else -> IPV4Subnet(IPV4Address(lastSubnet!!.broadcastAddress.next().intValue, mask))
            }.also { lastSubnet = it }
        }
    }

    private val requiredMasks: List<IPV4Mask>
        get() = hostCounts.sortedDescending().map { IPV4Mask(32.minus(it.findAppropriatePower()).toInt()) }

    /**
     * Finds the exponent that if `2` is raised with, that number will be greater or equal to this number.
     */
    private fun Int.findAppropriatePower(): Long {
        return ceil(log(this.toDouble(), 2.0)).toLong()
    }

    /**
     * Calculates the "next" IPv4 address (incremented by one)
     */
    private fun IPV4Address.next(): IPV4Address {
        return IPV4Address(intValue + 1, mask)
    }
}