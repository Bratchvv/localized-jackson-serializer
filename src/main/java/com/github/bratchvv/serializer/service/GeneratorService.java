package com.github.bratchvv.serializer.service;

import com.github.bratchvv.serializer.dto.Item;
import com.github.bratchvv.serializer.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simple service for generate random data and test it.
 *
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GeneratorService {

    private final ObjectMapper defaultObjectMapper;

    /**
     * Generate random DTO and serialize it to string.
     *
     * @param locale IETF BCP 47 language tag string.
     * @return JSON string
     */
    @SneakyThrows
    public String generateAndMapOrder(String locale) {
        return defaultObjectMapper.writeValueAsString(generateOrder(locale));
    }

    /**
     * Generate random DTO.
     *
     * @param locale IETF BCP 47 language tag string.
     * @return {@link OrderDto}
     */
    public OrderDto generateOrder(String locale) {
        var dto = new OrderDto();
        dto.setName("test_name_" + getRandomInt(100, 100_000));
        dto.setLocale(locale);
        dto.setDate(new Date());
        dto.setClientTime(OffsetDateTime.now());
        dto.setCreationTime(LocalDateTime.now());
        dto.setDay(LocalDate.now());
        dto.setTax(getRandomDouble(10.5, 100.50));
        dto.setTags(Sets.newHashSet(
                "test_" + getRandomInt(1, 100),
                "test_" + getRandomInt(1, 100)));

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < getRandomInt(4, 10); i++) {
            var item = Item.builder()
                    .id(String.valueOf(getRandomInt(100, 100_000)))
                    .date(LocalDateTime.now().minusDays(getRandomInt(1, 100)))
                    .name("test_item_name_" + getRandomInt(100, 100_000))
                    .price(BigDecimal.valueOf(getRandomDouble(100.5, 10_000.50)))
                    .locale(locale)
                    .build();
            items.add(item);
        }
        dto.setItems(items);
        return dto;
    }

    private double getRandomDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    private int getRandomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }
}
