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

package com.subnetting

/**
 * Regular expression representing an Ipv4 address (or a subnet mask in the _dot decimal notation_)
 */
const val IPV4_ADDRESS_PATTERN = "\\d+\\.\\d+\\.\\d+\\.\\d+"

/**
 * Regular expression representing a subnet mask written with the _routing prefix_
 */
const val MASK_CIDR_PATTERN = "/\\d+"