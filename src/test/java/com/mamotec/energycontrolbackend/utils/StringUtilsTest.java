package com.mamotec.energycontrolbackend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
class StringUtilsTest {

    @Test
    void toArray() {
        String s = "[982,6478]";
        int[] expected = {982, 6478};

        int[] actual = StringUtils.toArray(s);

        assertArrayEquals(expected, actual);
    }

}