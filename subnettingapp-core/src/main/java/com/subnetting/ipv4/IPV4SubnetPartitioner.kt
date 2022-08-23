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

import kotlin.math.pow

class IPV4SubnetPartitioner(baseIp: IPV4Address, val hostCounts: List<Int>) {

    private val baseIp = IPV4Subnet(baseIp).subnetAddress

    val subnets: List<IPV4Subnet>
        get() {
            var lastSubnet: IPV4Subnet? = null
            return requiredMasks.map { mask ->
                when (lastSubnet) {
                    null -> IPV4Subnet(IPV4Address(this.baseIp.ipValue, mask))
                    else -> IPV4Subnet(IPV4Address(lastSubnet!!.broadcastAddress.next().ipValue, mask))
                }.also { lastSubnet = it }
            }
        }

    private val requiredMasks: List<IPV4Mask>
        get() = hostCounts.sortedDescending().map { IPV4Mask(32.minus(it.findAppropriatePower()).toInt()) }

    private fun Int.findAppropriatePower(): Long {
        // TODO: make this algorithm more efficient: O(log n) instead of O(n)
        var power = 0.0
        var i = 0.0
        while (power.minus(2).minus(this) < 0)
            power = 2.0.pow(i++)
        return (i -1).toLong()
    }

    private fun IPV4Address.next(): IPV4Address {
        return IPV4Address(ipValue + 1, mask)
    }

}