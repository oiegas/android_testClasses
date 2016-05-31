package com.example.lenovo.testclasses;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class DateFormats {

    private static final java.text.DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);
    private static final java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static java.text.DateFormat iso8601() {
        return iso8601Format;
    }

    public static java.text.DateFormat justDate() {
        return dateFormat;
    }
}
