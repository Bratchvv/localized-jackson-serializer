package com.github.bratchvv.serializer.dto;

import com.github.bratchvv.serializer.transform.*;
import com.github.bratchvv.serializer.transform.api.DateLocalized;
import com.github.bratchvv.serializer.transform.api.Localized;
import com.github.bratchvv.serializer.transform.api.MoneyLocalized;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Simple item for test
 *
 * @author Vladimir Bratchikov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = LocaleFormatSerializer.class)
@Builder
public class Item implements Localized {

    private String id;

    private String name;

    @Getter(onMethod_ = {@DateLocalized})
    private LocalDateTime date;

    @Getter(onMethod_ = {@MoneyLocalized})
    private BigDecimal price;

    String locale;
}
