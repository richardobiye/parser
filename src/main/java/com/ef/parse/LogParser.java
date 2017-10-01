package com.ef.parse;

import com.ef.db.IpBlocked;
import com.ef.db.bal.DataTransactionsBal;
import com.ef.db.bal.balImpl.DataTransactionsBalImpl;
import com.ef.xmlsettings.XmlProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Xervier on 9/29/2017.
 */
public class LogParser {
    private List<IpDetails> ipDetailsList;
    private DataTransactionsBal dataTransactionsBal;

    static String getAccessLogRegex() {
        String regex1 = "^([\\d.]+)";
        String regex2 = " (\\S+)";
        String regex3 = " (\\S+)";
        String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]";
        String regex5 = " \"(.+?)\"";
        String regex6 = " (\\d{3})";
        String regex7 = " (\\d+|(.+?))";
        String regex8 = " \"([^\"]+|(.+?))\"";
        String regex9 = " \"([^\"]+|(.+?))\"";

        return regex1 + regex2 + regex3 + regex4 + regex5 + regex6 + regex7;//+regex8+regex9;
    }

    public void ApacheAccessLogParser(Date startDate, String duration,
                                      int threshold) throws ParseException, IOException {
        XmlProperties xmlProperties=new XmlProperties();
        String accessLogFilePath = xmlProperties.getFilePath();
        BufferedReader bufferReader = new BufferedReader(new FileReader(accessLogFilePath));

        long index = 0;
        Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher accessLogEntryMatcher;
        ipDetailsList = new ArrayList<>();
        String bufferReaderLine = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((bufferReaderLine = bufferReader.readLine()) != null) {
            stringBuilder.append(bufferReaderLine);
        }

        for (String logLine : stringBuilder.toString().split("\\|")) {
            index++;
            accessLogEntryMatcher = accessLogPattern.matcher(logLine);
            if (!accessLogEntryMatcher.matches()) {
                System.out.println("" + logLine + "\n : couldn't be parsed");
                continue;
            } else {
                Date logDate = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z").parse(accessLogEntryMatcher.group(4));
                if (logDate.after(startDate)) {
                    IpDetails ipDetails = new IpDetails(accessLogEntryMatcher.group(1),
                            accessLogEntryMatcher.group(5), logDate, accessLogEntryMatcher.group(5));
                    ipDetailsList.add(ipDetails);
                }
            }
        }
        bufferReader.close();
        ProcessIps processIps = new ProcessIps(ipDetailsList, startDate, duration, threshold);
        List<BlockedIps> blockedIps = processIps.getBlockedIps();
        List<IpBlocked> ipBlockeds = new ArrayList<>();
        System.out.println("=====================================================");
        System.out.println("--------------------Blocked Ips----------------------");
        System.out.println("=====================================================");
        blockedIps.forEach(blockedIps1 -> {
            System.out.println(blockedIps1.getIp().trim() + "\t\t" + blockedIps1.getThresholdLevel().getValue());
            IpBlocked ipBlocked = new IpBlocked(0, blockedIps1.getIp(), blockedIps1.getThresholdLevel().getValue(), duration, threshold);
            ipBlockeds.add(ipBlocked);
        });
        System.out.println("*****************************************************");
        //Database part
         new DataTransactionsBalImpl().getInstance().createTable();
        new DataTransactionsBalImpl().getInstance().saveIpDetails(ipBlockeds);
        System.out.println("=====================================================");
        System.out.println("--------------Printing from Database---------------");
        System.out.println("=====================================================");
        new DataTransactionsBalImpl().getInstance().getBlockedIpDetails(duration).forEach(blockedIps1 -> {
            System.out.println(blockedIps1.getIpAdress() + "\t" + blockedIps1.getReason());
        });
        System.out.println("*****************************************************");
    }
}
