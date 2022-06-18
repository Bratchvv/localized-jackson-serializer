package com.github.bratchvv.serializer.transform;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.github.bratchvv.serializer.transform.DateTransformUtils.formatDate;

@Slf4j
public class DateTransformUtilsTest {

    @Test
    public void formatDateTest() {

        Arrays.stream(Locale.getAvailableLocales()).forEach(l -> {
            log.info("lang: {}, LocalDateTime: {}", l.toLanguageTag(),
                    formatDate(LocalDateTime.now(), l.toLanguageTag(), 0));
        });

        Arrays.stream(Locale.getAvailableLocales()).forEach(l -> {
            log.info("lang: {}, LocalDate: {}", l.toLanguageTag(),
                    formatDate(LocalDate.now(), l.toLanguageTag(), 0));
        });

        Arrays.stream(Locale.getAvailableLocales()).forEach(l -> {
            log.info("lang: {}, OffsetDateTime: {}", l.toLanguageTag(),
                    formatDate(OffsetDateTime.now(), l.toLanguageTag(), 0));
        });

        Arrays.stream(Locale.getAvailableLocales()).forEach(l -> {
            log.info("lang: {}, Date: {}", l.toLanguageTag(),
                    formatDate(new Date(), l.toLanguageTag(), 0));
        });

        LocalDate localDate = LocalDate.of(2022, 6, 18);
        Assertions.assertEquals(formatDate(localDate, "en-US", 0), "Saturday, June 18, 2022");
        Assertions.assertEquals(formatDate(localDate, "fr-YT", 0), "samedi 18 juin 2022");
        Assertions.assertEquals(formatDate(localDate, "ga-GB", 0), "Dé Sathairn 18 Meitheamh 2022");
        Assertions.assertEquals(formatDate(localDate, "en-DK", 0), "Saturday, 18 June 2022");
        Assertions.assertEquals(formatDate(localDate, "zh-Hans-CN", 0), "2022年6月18日星期六");
        Assertions.assertEquals(formatDate(localDate, "uk-UA", 0), "субота, 18 червня 2022 р");
    }
}
