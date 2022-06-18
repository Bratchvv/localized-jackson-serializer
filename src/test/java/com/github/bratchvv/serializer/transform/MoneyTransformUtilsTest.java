package com.github.bratchvv.serializer.transform;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;

import static com.github.bratchvv.serializer.transform.MoneyTransformUtils.format;

@Slf4j
public class MoneyTransformUtilsTest {

    @Test
    public void formatIntegerNumberTest() {

        Arrays.stream(Locale.getAvailableLocales()).forEach(l -> {
            log.info("lang: {}, value: {}", l.toLanguageTag(),
                    format(BigDecimal.valueOf(1), 0, l.toLanguageTag()));
        });

        Assertions.assertEquals(format(BigDecimal.valueOf(5551.23), 0, "en-US"), "$5,551.00");
        Assertions.assertEquals(format(BigDecimal.valueOf(5551111.10), 2, "fr-YT"), "5 551 111,10 €");
        Assertions.assertEquals(format(BigDecimal.valueOf(5551111.45), 2, "ga-GB"), "£5,551,111.45");
        Assertions.assertEquals(format(BigDecimal.valueOf(5551111.35), 2, "en-DK"), "5.551.111,35 kr.");
        Assertions.assertEquals(format(BigDecimal.valueOf(5551111.22), 0, "zh-Hans-CN"), "¥5,551,111.00");
        Assertions.assertEquals(format(BigDecimal.valueOf(5551111.12), 2, "uk-UA"), "5 551 111,12 ₴");
    }


}
