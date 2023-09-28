package com.mamotec.energycontrolbackend.utils;

import java.util.function.Function;

public final class ConversionUtils {

    public static Function<Long, Long> conversionMethodBatteryPower() {
        return (x) -> {
            if (x >= 65536) {
                return x - 65536;
            }
            return x;
        };
    }
}
