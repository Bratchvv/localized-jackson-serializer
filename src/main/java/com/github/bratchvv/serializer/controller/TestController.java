package com.github.bratchvv.serializer.controller;

import com.github.bratchvv.serializer.dto.OrderDto;
import com.github.bratchvv.serializer.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST for testing.
 *
 * @author Vladimir Bratchikov
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final GeneratorService generatorService;

    /**
     * Test endpoint for enjoy.
     *
     * @param locale IETF BCP 47 language tag string.
     * @return Order json with formatted money and date values.
     */
    @GetMapping("/test/map")
    public OrderDto test1(@RequestParam String locale) {
        return generatorService.generateOrder(locale);
    }

    /**
     * Test endpoint for enjoy.
     *
     * @param locale IETF BCP 47 language tag string.
     * @return Json string with formatted money and date values.
     */
    @SneakyThrows
    @GetMapping("/test/string")
    public String test2(@RequestParam String locale) {
        return generatorService.generateAndMapOrder(locale);
    }

}
