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

package com.subnetting.ipv4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IPV4MaskTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void bitCountValidations(int bitCount) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new IPV4Mask(bitCount)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.255.0.0", "254.255.1.0", "-1.0.0.0", "1.1.1.1.1.1", "0.0.0.0", "/0", "/32"})
    void stringFormValidations(String stringFormat) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new IPV4Mask(stringFormat)
        );
    }

    @ParameterizedTest
    @MethodSource("intConversionArguments")
    void intConversion(int bitCount, int intValue) {
        assertEquals(intValue, new IPV4Mask(bitCount).getAsInt());
    }

    @ParameterizedTest
    @MethodSource("octetsAreRightArguments")
    void octetsAreRight(int bitCount, int octet1, int octet2, int octet3, int octet4) {
        var mask = new IPV4Mask(bitCount);
        assertEquals(octet1, mask.get(0), "First octet is calculated improperly!");
        assertEquals(octet2, mask.get(1), "Second octet is calculated improperly!");
        assertEquals(octet3, mask.get(2), "Third octet is calculated improperly!");
        assertEquals(octet4, mask.get(3), "Fourth octet is calculated improperly!");
    }

    @ParameterizedTest
    @MethodSource("stringConversionArguments")
    void stringConversion(int bitCount, String expectedCdirNotation, String expectedDotDecimal) {
        var mask = new IPV4Mask(bitCount);
        assertEquals(expectedCdirNotation, mask.toString(IPV4Mask.ToStringOption.CIDR_NOTATION));
        assertEquals(expectedDotDecimal, mask.toString(IPV4Mask.ToStringOption.DOT_DECIMAL_NOTATION));
    }

    public static List<Arguments> intConversionArguments() {
        return List.of(
                Arguments.of(8, 0xFF000000),
                Arguments.of(16, 0xFFFF0000),
                Arguments.of(24, 0xFFFFFF00),
                Arguments.of(6, 0xFC000000),
                Arguments.of(12, 0xFFF00000),
                Arguments.of(18, 0xFFFFC000),
                Arguments.of(27, 0xFFFFFFE0),
                Arguments.of(31, 0xFFFFFFFE)
        );
    }

    public static List<Arguments> octetsAreRightArguments() {
        return List.of(
                Arguments.of(8, 255, 0, 0, 0),
                Arguments.of(16, 255, 255, 0, 0),
                Arguments.of(24, 255, 255, 255, 0),
                Arguments.of(6, 252, 0, 0, 0),
                Arguments.of(12, 255, 240, 0, 0),
                Arguments.of(18, 255, 255, 192, 0),
                Arguments.of(27, 255, 255, 255, 224),
                Arguments.of(31, 255, 255, 255, 254)
        );
    }

    public static List<Arguments> stringConversionArguments() {
        return List.of(
                Arguments.of(8, "/8", "255.0.0.0"),
                Arguments.of(16, "/16", "255.255.0.0"),
                Arguments.of(24, "/24", "255.255.255.0"),
                Arguments.of(6, "/6", "252.0.0.0"),
                Arguments.of(12, "/12", "255.240.0.0"),
                Arguments.of(18, "/18", "255.255.192.0"),
                Arguments.of(27, "/27", "255.255.255.224"),
                Arguments.of(31, "/31", "255.255.255.254")
        );
    }
}
