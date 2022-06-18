package com.github.bratchvv.serializer.dto;

import com.github.bratchvv.serializer.transform.*;
import com.github.bratchvv.serializer.transform.api.DateLocalized;
import com.github.bratchvv.serializer.transform.api.Localized;
import com.github.bratchvv.serializer.transform.api.MoneyLocalized;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Simple DTO for test
 *
 * @author Vladimir Bratchikov
 */
@Data
@JsonSerialize(using = LocaleFormatSerializer.class)
public class OrderDto implements Localized {

    private String name;

    private List<Item> items;

    @Getter(onMethod_ = {@MoneyLocalized}) // TODO this is wrong, but it's using for test
    private Set<String> tags;

    @Getter(onMethod_ = {@DateLocalized})
    private Date date;

    @Getter(onMethod_ = {@DateLocalized(format = DateFormat.LONG)})
    private LocalDate day;

    @Getter(onMethod_ = {@DateLocalized})
    private LocalDateTime creationTime;

    @Getter(onMethod_ = {@DateLocalized})
    private OffsetDateTime clientTime;

    @Getter(onMethod_ = {@MoneyLocalized(decimalPlaces = 0)})
    private double tax;

    @MoneyLocalized
    private BigDecimal getTotalPrice() {
        return items.stream().map(Item::getPrice).reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    @MoneyLocalized
    public BigDecimal getTotalPriceWithTax() {
        return getTotalPrice().add(BigDecimal.valueOf(tax));
    }

    String locale;
}
