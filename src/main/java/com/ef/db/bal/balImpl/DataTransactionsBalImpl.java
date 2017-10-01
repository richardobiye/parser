package com.ef.db.bal.balImpl;

import com.ef.db.IpBlocked;
import com.ef.db.bal.DataTransactionsBal;
import com.ef.db.dal.DataTransactionsDal;
import com.ef.db.dal.dalImpl.DataTransactionsDalImpl;

import java.util.List;

/**
 * Created by Xervier on 10/1/2017.
 */
public class DataTransactionsBalImpl implements DataTransactionsBal {

    private static DataTransactionsBal dataTransactionsBal;

    public static DataTransactionsBal getInstance() {
        if (dataTransactionsBal == null) {
            dataTransactionsBal = new DataTransactionsBalImpl();
        }
        return dataTransactionsBal;
    }

    public static DataTransactionsDal getDataTransactionsDal() {
        return DataTransactionsDalImpl.getInstance();
    }

    @Override
    public void createTable() {
        getDataTransactionsDal().createTable();
    }

    @Override
    public void saveIpDetails(List<IpBlocked> ipBlockeds) {
        getDataTransactionsDal().saveIpDetails(ipBlockeds);
    }

    @Override
    public List<IpBlocked> getBlockedIpDetails(String duration) {
        return getDataTransactionsDal().getBlockedIpDetails(duration);
    }
}
