package com.github.bratchvv.serializer.transform;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.isNull;


/**
 * Util class to transform dates ({@link Date},{@link LocalDate},{@link LocalDateTime},{@link OffsetDateTime})
 * to formatted one by specific locale.
 *
 * @see com.github.bratchvv.serializer.transform.LocaleFormatSerializer
 * @see com.github.bratchvv.serializer.transform.api.Localized
 * @author Vladimir Bratchikov
 */
@UtilityClass
public class DateTransformUtils {

    static String formatDate(LocalDate date, String locale, Integer format) {
        return getDateTimeFormat(locale, format).format(date);
    }

    static String formatDate(LocalDateTime date, String locale, Integer format) {
        return getDateTimeFormat(locale, format).format(date);
    }

    static String formatDate(OffsetDateTime date, String locale, Integer format) {
        return getDateTimeFormat(locale, format).format(date);
    }

    static String formatDate(Date date, String locale, Integer format) {
        return getDateFormat(locale, format).format(date);
    }

    private DateFormat getDateFormat(String locale, Integer format) {
        return DateFormat.getDateInstance(isNull(format) ? DateFormat.DEFAULT: format, getLocale(locale));
    }

    private DateTimeFormatter getDateTimeFormat(String locale, Integer format) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.values()[format]).localizedBy(getLocale(locale));
    }

    private static Locale getLocale(String locale) {
        return Locale.forLanguageTag(locale);
    }
}
