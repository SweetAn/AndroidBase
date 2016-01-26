package com.commons.support.util;

import android.content.Context;

import com.commons.support.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化数据帮助类
 * Created by qianjin on 2015/9/22.
 */
public class FromatUtil {

    public static String formatDouble(double number) {
        return String.format("%.2f", number);
    }

    public static String getShowDouble(double number) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return df.format(number);
    }


    public static final DateFormat AGO_FULL_DATE_FORMATTER = new SimpleDateFormat("MM月dd日");
    public static String getSimpleDate(Date date) {
        return AGO_FULL_DATE_FORMATTER.format(date);
    }
    public static String fromatRelativeDate(Context context, Date date) {
        Date now = new Date();
        String min = context.getString(R.string.min);
        String hour = context.getString(R.string.hour);
        String suffix = context.getString(R.string.suffix);
        // Seconds.
        long diff = (now.getTime() - date.getTime()) / 1000;
        if (diff < 60) {
            return context.getString(R.string.now);
        }
        // Minutes.
        diff /= 60;
        if (diff < 0) {
            diff = 1;
        }
        if (diff < 60) {
            // return prefix + diff + min + suffix;
            return diff + min + suffix;
        }
        // Hours.
        diff /= 60;
        if (diff < 24) {
            // return prefix + diff + hour + suffix;
            return diff + hour + suffix;
        }
        return AGO_FULL_DATE_FORMATTER.format(date);
    }


}
