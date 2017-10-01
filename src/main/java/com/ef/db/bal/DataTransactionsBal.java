package com.ef.db.bal;

import com.ef.db.IpBlocked;

import java.util.List;

/**
 * Created by Xervier on 10/1/2017.
 */
public interface DataTransactionsBal {
    void createTable();
    void saveIpDetails(List<IpBlocked> ipBlockeds);
    List<IpBlocked> getBlockedIpDetails(String duration);
}
