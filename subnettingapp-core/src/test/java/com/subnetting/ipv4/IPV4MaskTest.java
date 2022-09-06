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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IPV4MaskTest {

    @Test
    void bitCountValidations() {
        IntStream.of(-1, 0, 32).forEach(bitCount ->
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new IPV4Mask(bitCount)
                )
        );

    }

    @ParameterizedTest
    @MethodSource("intConversionArguments")
    void intConversion(int bitCount, int intValue) {
        assertEquals(intValue, new IPV4Mask(bitCount).getAsInt());
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
}
