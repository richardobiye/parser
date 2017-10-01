package com.ef;

import com.ef.parse.LogParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private static boolean validateThreshold(String threshold) {
        String pattern = "\\d{3,10}";
        return threshold.matches(pattern);
    }

    private static Map<String, String> map;

    private static void makeMap(String[] args) {
        map = new HashMap<>();
        for (String arg : args) {
            if (arg.contains("=")) {
                map.put(arg.substring(0, arg.indexOf('=')),
                        arg.substring(arg.indexOf('=') + 1));
            }
        }
    }

    public static void main(String[] args) {
        try {
            makeMap(args);
            String inputStartDate = map.get("--startDate");
            String duration = map.get("--duration");
            String threshold = map.get("--threshold");
            if (inputStartDate == null || duration == null || threshold == null) {
                System.out.println("Wrong command!");
                return;
            }
            LogParser logParser = new LogParser();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").parse(inputStartDate);
            if (duration.equalsIgnoreCase("hourly") || duration.equalsIgnoreCase("daily")) {
                if (validateThreshold(threshold)) {
                    if (Integer.parseInt(threshold) == 100 || Integer.parseInt(threshold) == 250) {
                        logParser.ApacheAccessLogParser(startDate, duration, Integer.parseInt(threshold));
                    } else {
                        System.out.println("Operation unknown.");
                    }
                } else {
                    System.out.println("Operation unknown.");
                }
            } else {
                System.out.println("Operation unknown.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
