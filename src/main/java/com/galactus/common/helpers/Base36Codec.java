package com.galactus.common.helpers;

public class Base36Codec {
    private static final Long baseNumber = 1_000_000L;

    public static String generateUniqueId(String prefix, Long uniqueId) {
        var base36Encoded = Long.toString(uniqueId + baseNumber, 36);
        return String.format("%s%s", prefix, base36Encoded);
    }

    public static String toBase36(Long uniqueId) {
        return Long.toString(uniqueId, 36);
    }

    public static long fromBase36(String base36) {
        return Long.parseLong(base36, 36);
    }
}
