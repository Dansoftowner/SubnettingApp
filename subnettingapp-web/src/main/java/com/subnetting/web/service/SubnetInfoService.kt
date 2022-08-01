package com.subnetting.web.service

import com.subnetting.ipv4.IPV4Address
import com.subnetting.ipv4.IPV4Mask
import com.subnetting.ipv4.IPV4Subnet
import com.subnetting.web.domain.Entry
import org.springframework.stereotype.Service

@Service
class SubnetInfoService {

    fun getEntry(ip: String, mask: String): Entry {
        val ipAddress = IPV4Address(ip, IPV4Mask(mask))
        val subnet = IPV4Subnet(ipAddress)
        return subnet.run {
            Entry(
                subnetAddress.toString(),
                firstHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
                lastHostAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
                broadcastAddress.toString(IPV4Address.ToStringOption.ONLY_ADDRESS),
                subnetAddress.mask.toString(IPV4Mask.ToStringOption.DOT_DECIMAL_NOTATION)
            )
        }
    }

}