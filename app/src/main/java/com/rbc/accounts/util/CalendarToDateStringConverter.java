package com.rbc.accounts.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CalendarToDateStringConverter {

    private static CalendarToDateStringConverter INSTANCE = null;

    private CalendarToDateStringConverter() { }

    public static CalendarToDateStringConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CalendarToDateStringConverter();
        }
        return INSTANCE;
    }

    public String format(Long timeInMillis) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(timeInMillis);
    }

    public String formatToDateListItem(Long timeInMillis) {
        return new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
                .format(timeInMillis);
    }
}
