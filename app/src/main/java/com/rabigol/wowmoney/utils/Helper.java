package com.rabigol.wowmoney.utils;

import android.os.Build;
import android.text.format.DateFormat;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Artur.Ziaev on 25.10.2016.
 */

public class Helper {
    public static String nameInitials(String name) {
        if (name != null) {
            String[] parts = name.split(" ");
            if (parts.length >= 2) {
                String result = (parts[0].length() > 0 ? String.valueOf(parts[0].charAt(0)) : "") + (parts[1].length() > 0 ? String.valueOf(parts[1].charAt(0)) : "");
                if (!result.equals("")) {
                    return result;
                } else {
                    return "?";
                }
            }
        }
        return "?";
    }

    public static String formatTimestampToDateTime(int seconds) {
        Calendar server = Calendar.getInstance();
        server.setTimeInMillis((long) seconds * 1000);

        Calendar local = Calendar.getInstance();
        local.setTimeInMillis(System.currentTimeMillis());

        String date = DateFormat.format("dd.MM", server).toString();
        String time = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 ? DateFormat.format("kk:mm", server).toString() : DateFormat.format("HH:mm", server).toString();

        return date + " " + time;
    }

    public static String formatTimestampToDateShort(int seconds) {
        Calendar server = Calendar.getInstance();
        server.setTimeInMillis((long) seconds * 1000);

        String date = DateFormat.format("dd.MM", server).toString();

        return date;
    }

    public static String formatTimestampToDate(int seconds) {
        Calendar server = Calendar.getInstance();
        server.setTimeInMillis((long) seconds * 1000);

        String date = DateFormat.format("dd.MM.yyyy", server).toString();

        return date;
    }
}
