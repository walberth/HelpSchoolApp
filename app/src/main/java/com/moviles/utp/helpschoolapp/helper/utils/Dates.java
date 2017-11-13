package com.moviles.utp.helpschoolapp.helper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gustavo Ramos M. on 12/11/2017.
 */

public class Dates {

    public static final String MONTH_NAME[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo",
            "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    public static Date convertDateWithFormat(String date, SimpleDateFormat formatter) throws ParseException {
        return formatter.parse(date);
    }

    public static String convertDateWithFormat(Date date, SimpleDateFormat formatter) {
        return formatter.format(date);
    }

    public static Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getExtractDateFromCalendar(Calendar calendar, int extract) {
        return calendar.get(extract);
    }

    public static String getNameMonthFromDate(Calendar calendar) {
        return MONTH_NAME[calendar.get(Calendar.MONTH)];
    }

    public static String getFormatNameCardView(Calendar calendar) {
        return calendar.get(Calendar.DATE) + " de "
                + Dates.MONTH_NAME[calendar.get(Calendar.MONTH)] + " de "
                + calendar.get(Calendar.YEAR);
    }
}
