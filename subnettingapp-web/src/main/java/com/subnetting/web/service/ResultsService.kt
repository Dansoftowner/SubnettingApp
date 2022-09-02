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

package com.subnetting.web.service

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet
import com.subnetting.ipv4.IPV4SubnetPartitioner
import com.subnetting.web.domain.Entry
import org.springframework.stereotype.Service

/**
 * The service responsible for processing the entries (subnets) that will be shown for the user.
 */
@Service
class ResultsService {

    fun getEntries(
        ip: String,
        mask: String,
        task: String,
        hostCounts: String?
    ): List<Entry> = when (task) {
        "si" -> listOf(getEntry(ip, mask))
        "sp" -> getPartitionedEntries(
            ip, mask,
            hostCounts!!
                .split(",")
                .map(String::trim)
                .map(String::toInt)
        )
        else -> throw IllegalArgumentException() // TODO: message
    }


    private fun getPartitionedEntries(ip: String, mask: String, hostCounts: List<Int>): List<Entry> {


        val maskObj = try {
            IPV4Mask(mask)
        }  catch (e: IllegalArgumentException) {
            throw RuntimeException("Your mask is in invalid format!")
        }


        val ipObj = try {
            IPV4Address(ip, maskObj)
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("Your IP is in invalid format!")
        }

        return IPV4SubnetPartitioner(ipObj, hostCounts).subnets.map { it.toEntry() }
    }


    private fun getEntry(ip: String, mask: String): Entry {
        val ipAddress = IPV4Address(ip, IPV4Mask(mask))
        val subnet = IPV4Subnet(ipAddress)
        return subnet.toEntry()
    }

    private fun IPV4Subnet.toEntry() = Entry(
        subnetAddress.toString(),
        firstHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
        lastHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
        broadcastAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
        subnetAddress.mask.toString(IPV4Mask.ToStringOption.DOT_DECIMAL_NOTATION)
    )

}