package com.moviles.utp.helpschoolapp.helper.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Gustavo Ramos M. on 12/11/2017.
 */

public class FormatDate {
    
    public static SimpleDateFormat getDateFormatAmerican() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    public static SimpleDateFormat getDateFormatLocale() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }

    public static SimpleDateFormat getDateFormatAmericanUntilSeconds() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    }

    public static SimpleDateFormat getDateFormatLocaleUntilSeconds() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    }
}
