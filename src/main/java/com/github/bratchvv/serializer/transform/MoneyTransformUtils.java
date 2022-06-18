package com.github.bratchvv.serializer.transform;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Util class to transform money values ({@link BigDecimal},{@link BigInteger},{@link Double})
 * to formatted one by specific locale.
 *
 * @see com.github.bratchvv.serializer.transform.LocaleFormatSerializer
 * @see com.github.bratchvv.serializer.transform.api.Localized
 * @author Vladimir Bratchikov
 */
@UtilityClass
public class MoneyTransformUtils {

    static String format(BigDecimal number, Integer decimalPlaces, String locale) {
        return getNumberInstance(locale).format(number.setScale(getDecimalPlaces(decimalPlaces), RoundingMode.HALF_UP));
    }

     static String format(Double number, Integer decimalPlaces, String locale) {
        return format(BigDecimal.valueOf(number), decimalPlaces, locale);
    }

    static String format(BigInteger number, String locale) {
        return getNumberInstance(locale).format(number);
    }

    private static Integer getDecimalPlaces(Integer decimalPlaces) {
        decimalPlaces = decimalPlaces == null || decimalPlaces < 0 ? 2 : decimalPlaces;
        return decimalPlaces;
    }

    private static NumberFormat getNumberInstance(String locale) {
        Locale localeInst = getLocale(locale);
        return NumberFormat.getCurrencyInstance(localeInst);
    }


    private static Locale getLocale(String locale) {
        return Locale.forLanguageTag(locale);
    }

}
