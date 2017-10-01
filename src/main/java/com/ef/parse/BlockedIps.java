package com.ef.parse;

import com.ef.enums.ThresholdLevel;

/**
 * Created by Xervier on 9/28/2017.
 */
public class BlockedIps {
    private String ip;
    private ThresholdLevel thresholdLevel;

    public BlockedIps() {
    }

    public BlockedIps(String ip, ThresholdLevel thresholdLevel) {
        this.ip = ip;
        this.thresholdLevel = thresholdLevel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ThresholdLevel getThresholdLevel() {
        return thresholdLevel;
    }

    public void setThresholdLevel(ThresholdLevel thresholdLevel) {
        this.thresholdLevel = thresholdLevel;
    }
}
