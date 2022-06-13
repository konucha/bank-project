package com.example.bankakprind.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CurrencyRupiah {

    public static String format(double angka) {
        DecimalFormat currencyIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        currencyIndonesia.setDecimalFormatSymbols(formatRp);
        return currencyIndonesia.format(angka);
    }
}
