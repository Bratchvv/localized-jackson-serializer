package com.github.bratchvv.serializer.transform.api;

/**
 * Basic interface which add ability to use locale code.
 * ATTENTION! IETF BCP 47 language tag string has to be used!
 *
 * @see com.github.bratchvv.serializer.transform.LocaleFormatSerializer
 * @author Vladimir Bratchikov
 */
public interface Localized {

    /**
     * @return IETF BCP 47 language tag string
     */
    String getLocale();
}
