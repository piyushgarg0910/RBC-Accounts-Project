package com.rbc.accounts.util;

import java.text.NumberFormat;
import java.util.Locale;

import javax.annotation.Nullable;

public class StringToCurrencyConverter {

    private static StringToCurrencyConverter INSTANCE = null;

    private StringToCurrencyConverter() { }

    public static StringToCurrencyConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StringToCurrencyConverter();
        }
        return INSTANCE;
    }

    public String format(@Nullable String amount) {
        if (amount == null) {
            return "";
        }
        return NumberFormat
                .getCurrencyInstance(Locale.getDefault()).format(Float.parseFloat(amount));
    }
}