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
 *
 * @param <T>
 */
@Slf4j
public class LocaleFormatSerializer<T extends Localized> extends JsonSerializer<T> {

    private static final String GET_KEY = "get";

    /**
     *
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     */
    @SneakyThrows
    @Override
    public void serialize(Localized value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        jsonGenerator.writeStartObject();

        var locale = value.getLocale();
        for (Method method : value.getClass().getMethods()) {
            var name = method.getName();
            if (name.startsWith(GET_KEY) && name.length() > 3) {
                var field = getFieldName(name);
                var getterValue = method.invoke(value);
                jsonGenerator.writeObjectField(field, getterValue);

                for (Annotation annotation : method.getAnnotations()) {
                    processAnnotation(jsonGenerator, locale, field, getterValue, annotation);
                }
            }
        }
        jsonGenerator.writeEndObject();
    }

    /**
     *
     * @param jsonGenerator
     * @param locale
     * @param field
     * @param getterValue
     * @param annotation
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
     *
     * @param jsonGenerator
     * @param locale
     * @param field
     * @param getterValue
     * @param dateLocalized
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
        }
    }

    private String buildName(String field) {
        return field + "Formatted";
    }

    /**
     *
     * @param jsonGenerator
     * @param locale
     * @param field
     * @param getterValue
     * @param moneyLocalized
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
        }
    }

    /**
     *
     * @param name
     * @return
     */
    private String getFieldName(String name) {
        var c = name.substring(3).toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }
}
