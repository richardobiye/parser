package com.ef.db.dal;

import com.ef.db.IpBlocked;

import java.util.List;

/**
 * Created by Xervier on 10/1/2017.
 */
public interface DataTransactionsDal {
    void createTable();
    void saveIpDetails(List<IpBlocked> ipBlockeds);
    List<IpBlocked> getBlockedIpDetails(String duration);
}
