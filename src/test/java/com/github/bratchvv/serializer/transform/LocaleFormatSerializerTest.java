package com.github.bratchvv.serializer.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bratchvv.serializer.dto.Item;
import com.github.bratchvv.serializer.dto.OrderDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class LocaleFormatSerializerTest {

    private static ObjectMapper defaultObjectMapper;

    @BeforeAll
    public static void init() {
        defaultObjectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @SneakyThrows
    @Test
    @SuppressWarnings("all")
    public void testSerializer() {
        OrderDto dto = getDto("en-US");
        String s = defaultObjectMapper.writeValueAsString(dto);
        var map = defaultObjectMapper.readValue(s, Map.class);
        Assertions.assertEquals(map.get("dateFormatted"), "Jun 18, 2022");
        Assertions.assertEquals(map.get("dayFormatted"), "June 18, 2022");
        Assertions.assertEquals(map.get("taxFormatted"), "$101.00");
        Assertions.assertEquals(map.get("totalPriceWithTaxFormatted"), "$5,501.00");
        ArrayList<Map> items = (ArrayList<Map>) map.get("items");
        Assertions.assertEquals(items.get(0).get("dateFormatted"), "Jun 18, 2022");

        dto = getDto("ga-GB");
        s = defaultObjectMapper.writeValueAsString(dto);
        map = defaultObjectMapper.readValue(s, Map.class);
        Assertions.assertEquals(map.get("dateFormatted"), "18 Meith 2022");
        Assertions.assertEquals(map.get("dayFormatted"), "18 Meitheamh 2022");
        Assertions.assertEquals(map.get("taxFormatted"), "£101.00");
        Assertions.assertEquals(map.get("totalPriceWithTaxFormatted"), "£5,501.00");
        items = (ArrayList<Map>) map.get("items");
        Assertions.assertEquals(items.get(0).get("dateFormatted"), "18 Meith 2022");
    }

    private OrderDto getDto(String locale) {
        var dto = new OrderDto();
        dto.setName("test_name");
        dto.setLocale(locale);
        dto.setDate(new Date(1655564499409L));
        dto.setDay(LocalDate.of(2022, 6, 18));
        dto.setTax(100.50);
        List<Item> items = new ArrayList<>();
        var item = Item.builder()
                .id("1")
                .date(LocalDateTime.of(2022, 6, 18, 1, 2, 3))
                .name("test_item_name")
                .price(BigDecimal.valueOf(5400.5))
                .locale(locale)
                .build();
        items.add(item);
        dto.setItems(items);
        return dto;
    }
}
