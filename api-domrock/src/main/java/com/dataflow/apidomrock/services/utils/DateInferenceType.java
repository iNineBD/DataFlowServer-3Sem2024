package com.dataflow.apidomrock.services.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateInferenceType {

    private static final String[] DATE_FORMATS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
            "HH:mm:ss.SSS",
            "HH:mm:ss",
            "dd-MM-yyyy'T'HH:mm:ss.SSSXXX",
            "dd-MM-yyyy'T'HH:mm:ss.SSS",
            "dd-MM-yyyy'T'HH:mm:ssXXX",
            "dd-MM-yyyy'T'HH:mm:ss",
            "dd-MM-yyyy HH:mm:ss",
            "dd/MM/yyyy",
            "dd.MM.yyyy",
            "dd MM yyyy",
            "MM/dd/yyyy'T'HH:mm:ss.SSSXXX",
            "MM/dd/yyyy'T'HH:mm:ss.SSS",
            "MM/dd/yyyy'T'HH:mm:ssXXX",
            "MM/dd/yyyy'T'HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss",
            "MM-dd-yyyy",
            "MM.dd.yyyy",
            "MM dd yyyy",
            "dd MMMM yyyy",
            "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm",
            "dd/MM/yyyy HH:mm:ss.SSS",
            "dd/MM/yyyy HH'h'mm",
            "dd/MM/yyyy hh'h'mm",
            "dd/MM/yyyy HH:mm:ss zzz",
            "dd/MM/yyyy HH:mm:ss zzzz",
    };

    public static boolean isDate(String input) {
        for (String format : DATE_FORMATS) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                Date date = sdf.parse(input);
                return true;
            } catch (ParseException e) {
                continue;
            }
        }
        return false;

    }
}
