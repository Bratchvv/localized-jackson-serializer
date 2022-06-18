package com.github.bratchvv.serializer.transform;

import com.github.bratchvv.serializer.transform.api.DateLocalized;
import com.github.bratchvv.serializer.transform.api.Localized;
import com.github.bratchvv.serializer.transform.api.MoneyLocalized;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Universal serializer that typically serializes all fields,
 * but also generate new fields with date and money values formatted by locale.
 *
 * @param <T>
 * @author Vladimir Bratchikov
 * @see com.github.bratchvv.serializer.transform.api.Localized
 * @see com.github.bratchvv.serializer.transform.api.MoneyLocalized
 * @see com.github.bratchvv.serializer.transform.api.DateLocalized
 */
@Slf4j
public class LocaleFormatSerializer<T extends Localized> extends JsonSerializer<T> {

    private static final String GET_KEY = "get";

    /**
     * Custom implementation of {@link JsonSerializer}
     *
     * @param value data based on {@link Localized}
     * @param jsonGenerator json generator
     * @param serializerProvider serializer provider
     */
    @SneakyThrows
    @Override
    public void serialize(Localized value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        jsonGenerator.writeStartObject();

        var locale = value.getLocale();
        for (Method method : value.getClass().getMethods()) {
            var name = method.getName();
            if (name.startsWith(GET_KEY) && name.length() > 3) { // use only getters
                var field = getFieldName(name);
                var getterValue = method.invoke(value); // get orig getter value
                jsonGenerator.writeObjectField(field, getterValue);

                for (Annotation annotation : method.getAnnotations()) {
                    processAnnotation(jsonGenerator, locale, field, getterValue, annotation);
                }
            }
        }
        jsonGenerator.writeEndObject();
    }

    /**
     * Process annotations factory for mapping formatted values.
     *
     * @param jsonGenerator json generator
     * @param locale locale string (IETF BCP 47)
     * @param field orig field name
     * @param getterValue orig field value
     * @param annotation processing annotation.
     * @throws IOException
     */
    private void processAnnotation(JsonGenerator jsonGenerator, String locale, String field, Object getterValue,
                                   Annotation annotation) throws IOException {
        if (annotation instanceof MoneyLocalized moneyLocalized) {
            mapMoney(jsonGenerator, locale, field, getterValue, moneyLocalized);
        } else if (annotation instanceof DateLocalized dateLocalized) {
            mapDate(jsonGenerator, locale, field, getterValue, dateLocalized);
        }
    }

    /**
     * Try to map date value.
     *
     * @param jsonGenerator json generator
     * @param locale locale string (IETF BCP 47)
     * @param field orig field name
     * @param getterValue orig field value
     * @param dateLocalized DateLocalized annotation.
     * @throws IOException
     */
    private void mapDate(JsonGenerator jsonGenerator, String locale, String field,
                         Object getterValue, DateLocalized dateLocalized)
        throws IOException {
        if (getterValue instanceof Date d) {
            var formatNumber = DateTransformUtils.formatDate(d, locale, dateLocalized.format());
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else if (getterValue instanceof LocalDate d) {
            var formatNumber = DateTransformUtils.formatDate(d, locale, dateLocalized.format());
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else if (getterValue instanceof LocalDateTime d) {
            var formatNumber = DateTransformUtils.formatDate(d, locale, dateLocalized.format());
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else if (getterValue instanceof OffsetDateTime d) {
            var formatNumber = DateTransformUtils.formatDate(d, locale, dateLocalized.format());
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else {
            log.error("Unsupported data type with DateLocalized. Field: {}", field);
        }
    }

    /**
     * Build formatted field name.
     *
     * @param field orig field name
     * @return xxxxFormatted field name
     */
    private String buildName(String field) {
        return field + "Formatted";
    }


    /**
     * Try to map money value.
     *
     * @param jsonGenerator json generator
     * @param locale locale string (IETF BCP 47)
     * @param field orig field name
     * @param getterValue orig field value
     * @param moneyLocalized MoneyLocalized annotation.
     * @throws IOException
     */
    private void mapMoney(JsonGenerator jsonGenerator, String locale, String field,
                          Object getterValue, MoneyLocalized moneyLocalized)
        throws IOException {
        if (getterValue instanceof BigDecimal v) {
            var formatNumber = MoneyTransformUtils.format(v, moneyLocalized.decimalPlaces(), locale);
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else if (getterValue instanceof BigInteger v) {
            var formatNumber = MoneyTransformUtils.format(v, locale);
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else if (getterValue instanceof Double v) {
            var formatNumber = MoneyTransformUtils.format(v, moneyLocalized.decimalPlaces(), locale);
            jsonGenerator.writeObjectField(buildName(field), formatNumber);
        } else {
            log.error("Unsupported data type with MoneyLocalized. Field: {}", field);
        }
    }

    /**
     * Parse field name from getter.
     *
     * @param name getter name
     * @return orig field name
     */
    private String getFieldName(String name) {
        var c = name.substring(3).toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }
}
