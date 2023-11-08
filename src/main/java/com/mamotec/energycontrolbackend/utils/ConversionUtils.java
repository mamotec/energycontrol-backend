package com.mamotec.energycontrolbackend.utils;

import java.util.function.Function;

public final class ConversionUtils {

    public static Function<Long, Long> conversionMethodBatteryPower() {
        return (x) -> {
            if (x > 32768) {
                return x - 65536;
            }
            return x;
        };
    }

    public static Double convertWattsToAmps(Long watts, Long voltage) {
        return watts / (voltage * 1.0) / 3;
    }
}
