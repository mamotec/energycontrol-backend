package com.mamotec.energycontrolbackend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
class StringUtilsTest {

    @Test
    void toArray() {
        String s = "[982,6478]";
        double[] expected = {982, 6478};

        double[] actual = StringUtils.toArray(s);

        assertArrayEquals(expected, actual);
    }

}