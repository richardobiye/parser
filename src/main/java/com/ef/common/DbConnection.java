package com.ef.common;

/**
 * Created by Xervier on 9/30/2017.
 */
import com.ef.xmlsettings.XmlProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MAX_POOL = "250";

    private Connection connection;
    private Properties properties;

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            XmlProperties xmlProperties=new XmlProperties();
            properties.setProperty("user", xmlProperties.getUserName());
            properties.setProperty("password", xmlProperties.getPassWord());
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                XmlProperties xmlProperties=new XmlProperties();
                connection = DriverManager.getConnection(xmlProperties.getConnection(), getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
