package com.subnetting

/**
 * Regular expression representing an Ipv4 address (or a subnet mask in the _dot decimal notation_)
 */
const val IPV4_ADDRESS_PATTERN = "\\d+\\.\\d+\\.\\d+\\.\\d+"

/**
 * Regular expression representing a subnet mask written with the _routing prefix_
 */
const val MASK_ROUTING_PREFIX_PATTERN = "/\\d+"

@Deprecated("Use kotlin's Int.countOneBits()", level = DeprecationLevel.ERROR)
fun octetBitCount(octet: UByte): Int {
    var n = octet
    var count = 0
    while (n > 0u) {
        count++
        n = n.and((n - 1u).toUByte())
    }

    // 10 - 00001010
    // 10 > 0 ? yes -> count = 1

    // 10 & 9 -> removes the least significant 1
    //   00001010
    // & 00001001
    //   00001000
    // 8 > 0 ? yes -> count = 2

    // 8 & 7
    //   00001000
    // & 00000111
    //   00000000
    // 0 > 0 ? no -> count stays 2

    return count
}