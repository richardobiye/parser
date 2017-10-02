package com.ef.xmlsettings;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Xervier on 10/1/2017.
 */
public class XmlProperties {
    private static Map<String, String> map;

    public XmlProperties() {
        getProperties();
    }

    public String getUserName() {
        return map.get("databaseUserName");
    }

    public String getPassWord() {
        return map.get("databasePassword");
    }

    public String getConnection() {
        return map.get("databaseConnection");
    }

    public String getFilePath() {
        return map.get("accessLogFilePath");
    }

    private void getProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = getFile(); //new File(classLoader.getResource("settings.xml").getFile());
            FileInputStream fileInput = new FileInputStream(file);


            Properties properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();

            Enumeration enuKeys = properties.keys();
            map = new HashMap<>();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                map.put(key, value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        File file = null;
        String resource = "/settings.xml";
        URL res = getClass().getResource(resource);
        if (res.toString().startsWith("jar:")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];
                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            file = new File(res.getFile());
        }
        if (file != null && !file.exists()) {
            throw new RuntimeException("Error: File " + file + " not found!");
        }
        return file;
    }
}
