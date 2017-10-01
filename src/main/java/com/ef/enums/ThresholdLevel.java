package com.ef.enums;

/**
 * Created by Xervier on 9/28/2017.
 */
public enum ThresholdLevel {
    OVER_100("Blocked. Made more than 100 request "),
    OVER_200("Blocked. Made more than 250 request ");

    private final String value;

    ThresholdLevel(String value) {
        this.value = value;
    }

    public static ThresholdLevel valueOfName(String thresholdLevel) {
        for (ThresholdLevel type : values()) {
            if ( thresholdLevel.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Wrong argument");
    }

    public String getValue() {
        return value;
    }
}
