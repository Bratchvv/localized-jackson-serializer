package com.github.bratchvv.serializer.transform.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to mark getters which should be transformed in formatted and localized money string with currency.
 *
 * @see com.github.bratchvv.serializer.transform.LocaleFormatSerializer
 * @author Vladimir Bratchikov
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoneyLocalized {

    /**
     * @return decimal places count.
     */
    int decimalPlaces() default 2;
}
