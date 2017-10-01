package com.ef.parse;

/**
 * Created by Xervier on 9/28/2017.
 */
public class IpStatistics {
    private String ip;
    private int count;
    private Duration duration;

    public IpStatistics(String ip, int count, Duration duration) {
        this.ip = ip;
        this.count = count;
        this.duration=duration;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
