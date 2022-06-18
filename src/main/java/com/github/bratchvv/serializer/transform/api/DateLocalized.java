package com.github.bratchvv.serializer.transform.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DateFormat;


/**
 * An annotation to mark getters which should be transformed in formatted and localized date string.
 *
 * @see com.github.bratchvv.serializer.transform.LocaleFormatSerializer
 * @author Vladimir Bratchikov
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateLocalized {

    /**
     * Date format.
     *
     * @return DateFormat
     */
    int format() default DateFormat.DEFAULT;
}
