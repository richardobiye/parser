package com.ef.parse;

import com.ef.enums.ThresholdLevel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Xervier on 9/28/2017.
 */
public class ProcessIps {

    private String duration;
    private int threshold;
    private List<BlockedIps> blockedIps = new ArrayList<>();
    private Date startDate;
    List<IpDetails> ipDetailsList = new ArrayList<>();

    public ProcessIps() {
    }

    public List<BlockedIps> getBlockedIps() {
        return blockedIps;
    }

    public void setBlockedIps(List<BlockedIps> blockedIps) {
        this.blockedIps = blockedIps;
    }

    public ProcessIps(List<IpDetails> ipDetailsList, Date startDate, String duration,
                      int threshold) {
        this.ipDetailsList = ipDetailsList;
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        getProcessedIps();
    }

    public void getProcessedIps() {
        setBlockedIps(getIpsToBlock(getIpsStatistics(ipDetailsList, startDate)));
    }

    private List<BlockedIps> getIpsToBlock(List<IpStatistics> ipStatistics) {
        List<BlockedIps> blockedIpses = new ArrayList<>();
        if (threshold == 100) {
            ipStatistics.stream().filter(blockedIps -> blockedIps.getCount() > 3).forEach(blockedIps -> {
                blockedIpses.add(new BlockedIps(blockedIps.getIp(), ThresholdLevel.OVER_100));
            });
        } else {
            ipStatistics.stream().filter(blockedIps -> blockedIps.getCount() > 2).forEach(blockedIps -> {
                blockedIpses.add(new BlockedIps(blockedIps.getIp(), ThresholdLevel.OVER_200));
            });
        }
        return blockedIpses;
    }

    private List<IpStatistics> getIpsStatistics(List<IpDetails> ipDetailsList, Date startDate) {
        List<IpStatistics> ipStatistics = new ArrayList<>();
        Calendar initialDate = Calendar.getInstance();
        Calendar initialDatePlus1 = Calendar.getInstance();
        initialDate.setTime(startDate);
        initialDatePlus1.setTime(startDate);
        if (duration.equalsIgnoreCase("hourly")) {
            initialDatePlus1.add(Calendar.HOUR_OF_DAY, 1);
        } else {
            initialDatePlus1.add(Calendar.DATE, 1);
        }
        //cal.getTime();

        int ipSize = ipDetailsList.size();
        Date endDate;
        if (ipSize > 0) {
            //Get the last date of the last log entry
            Date listDate1 = ipDetailsList.get(0).getDate();
            Date listDate2 = ipDetailsList.get(ipSize - 1).getDate();
            if (listDate1.before(listDate2)) {
                endDate = listDate2;
            } else {
                endDate = listDate1;
            }
        } else {
            return null;
        }
        long totalPeriod = 0;
        if (duration.equalsIgnoreCase("hourly")) {
            totalPeriod = (-startDate.getTime() + endDate.getTime()) / (60 * 60 * 1000);
        } else {
            totalPeriod = ((-startDate.getTime() + endDate.getTime()) / (60 * 60 * 1000) / 24);
        }

        for (int i = 0; i <= totalPeriod; i++) {
            ipDetailsList.stream().filter(ipDetails -> (ipDetails.getDate().after(initialDate.getTime()) && ipDetails.getDate().before(initialDatePlus1.getTime()))).forEach(ipDetails ->
            {
                if (ipStatistics.size() == 0) {
                    ipStatistics.add(new IpStatistics(ipDetails.getIp(), 1, new Duration(initialDate.getTime(), initialDatePlus1.getTime())));
                } else {
                    final boolean[] ipExist = {false};
                    ipStatistics.forEach(blockedIps -> {
                        if (blockedIps.getIp().equalsIgnoreCase(ipDetails.getIp())) {
                            if (compareDurationObjects(blockedIps.getDuration(), new Duration(initialDate.getTime(), initialDatePlus1.getTime()))) {
                                blockedIps.setCount(blockedIps.getCount() + 1);
                                ipExist[0] = true;
                            }
                        }
                    });
                    if (!ipExist[0]) {
                        ipStatistics.add(new IpStatistics(ipDetails.getIp(), 1, new Duration(initialDate.getTime(), initialDatePlus1.getTime())));
                    }
                }
            });
            if (duration.equalsIgnoreCase("hourly")) {
                initialDatePlus1.add(Calendar.HOUR_OF_DAY, 1);
                initialDate.add(Calendar.HOUR_OF_DAY, 1);
            } else {
                initialDatePlus1.add(Calendar.DATE, 1);
                initialDate.add(Calendar.DATE, 1);
            }

        }
        return ipStatistics;
    }

    private boolean compareDurationObjects(Duration object1, Duration object2) {
        return object1.getStart().equals(object2.getStart()) && object1.getEnd().equals(object2.getEnd());
    }
}
