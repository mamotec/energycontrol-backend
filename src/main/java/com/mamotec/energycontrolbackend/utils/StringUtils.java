package com.mamotec.energycontrolbackend.utils;

import java.util.Arrays;

public class StringUtils {

    // String comes in the format of [982,6478]
    public static int[] toArray(String s) {
        String cleanedResponse = s.substring(1, s.length() - 1);

        return Arrays.stream(cleanedResponse.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
