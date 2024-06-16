package br.com.vital.controle_servico.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalFormat {

    public static String getBigDecimalToCurrencyFormatted(BigDecimal amount) {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("¤ ###,###,##0.00", symbols);
        decimalFormat.setCurrency(java.util.Currency.getInstance(new Locale("pt", "BR")));

        return decimalFormat.format(amount);
    }
    
}
