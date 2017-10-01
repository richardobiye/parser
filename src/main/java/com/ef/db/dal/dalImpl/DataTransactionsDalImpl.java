package com.ef.db.dal.dalImpl;

import com.ef.common.DbConnection;
import com.ef.db.IpBlocked;
import com.ef.db.dal.DataTransactionsDal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xervier on 10/1/2017.
 */
public class DataTransactionsDalImpl implements DataTransactionsDal {

    private static DataTransactionsDal dataTransactionsDal;

    public static DataTransactionsDal getInstance() {
        if (dataTransactionsDal == null) {
            dataTransactionsDal = new DataTransactionsDalImpl();
        }
        return dataTransactionsDal;
    }

    @Override
    public void createTable() {
        try {
            DbConnection dbConnection = new DbConnection();
            Connection conn = dbConnection.connect();
            Statement statement = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS `blockedipsmaster` (\n" +
                    "  `id` int(11) NOT NULL auto_increment,   \n" +
                    "  `ipadress` varchar(100)  NOT NULL default '',     \n" +
                    "  `duration`  varchar(10)  NOT NULL default '',     \n" +
                    "  `threshold` int(11)  NULL,    \n" +
                    "  `reason`  varchar(250) NOT NULL default '',\n" +
                    "   PRIMARY KEY  (`id`)\n" +
                    ");";
            statement.executeQuery(query);
            dbConnection.disconnect();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void saveIpDetails(List<IpBlocked> ipBlockeds) {
        try {
            for (IpBlocked ipBlocked : ipBlockeds) {
                DbConnection dbConnection = new DbConnection();
                Connection conn = dbConnection.connect();
                Statement statement = conn.createStatement();
                String query = "INSERT INTO blockedipsmaster (ipadress,duration,threshold,reason) values ('" + ipBlocked.getIpAdress() + "'," + ipBlocked.getDuration() + "," + ipBlocked.getThreshold() + ",'" + ipBlocked.getReason() + "')";
                statement.executeQuery(query);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public List<IpBlocked> getBlockedIpDetails(String duration) {
        try {
            List<IpBlocked> ipBlockeds = new ArrayList<>();
            DbConnection dbConnection = new DbConnection();
            Connection conn = dbConnection.connect();
            Statement statement = conn.createStatement();
            String query = " SELECT id,ipadress,reason,duration,threshold FROM blockedipsmaster where duration =" + "\"" + duration + "\" ";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                IpBlocked ipBlocked = new IpBlocked(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
                ipBlockeds.add(ipBlocked);
            }
            return ipBlockeds;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
