package com.ef.parse;

import java.util.Date;

/**
 * Created by Xervier on 9/28/2017.
 */
public class IpDetails {
    private String ip;
    private String url;
    private Date date;
    private String http;

    public IpDetails() {
    }

    public IpDetails(String ip, String url, Date date, String http) {
        this.ip = ip;
        this.url = url;
        this.date = date;
        this.http = http;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }
}
