package com.subnetting.web.service

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet
import com.subnetting.ipv4.IPV4SubnetPartitioner
import com.subnetting.web.domain.Entry
import org.springframework.stereotype.Service

@Service
class SubnetInfoService {

    fun getEntries(
        ip: String,
        mask: String,
        task: String,
        hostCounts: String
    ): List<Entry> = when (task) {
        "si" -> listOf(getEntry(ip, mask))
        "sp" -> getPartitionedEntries(
            ip, mask,
            hostCounts
                .split(",")
                .map(String::trim)
                .map(String::toInt)
        )
        else -> throw IllegalArgumentException() // TODO: message
    }


    private fun getPartitionedEntries(ip: String, mask: String, hostCounts: List<Int>): List<Entry> =
        IPV4SubnetPartitioner(IPV4Address(ip, IPV4Mask(mask)), hostCounts).subnets.map { it.toEntry() }


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