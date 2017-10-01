package com.ef.db;

/**
 * Created by Xervier on 9/30/2017.
 */
public class IpBlocked {
    private int id;
    private String ipAdress;
    private String reason;
    private String duration;
    private int threshold;

    public IpBlocked() {
    }

    public IpBlocked(int id, String ipAdress, String reason, String duration, int threshold) {
        this.id = id;
        this.ipAdress = ipAdress;
        this.reason = reason;
        this.duration = duration;
        this.threshold = threshold;
    }

    public int getId() {
        return id;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public String getReason() {
        return reason;
    }

    public String getDuration() {
        return duration;
    }

    public int getThreshold() {
        return threshold;
    }

}
