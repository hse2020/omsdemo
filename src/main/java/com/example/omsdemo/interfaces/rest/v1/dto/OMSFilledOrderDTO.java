package com.example.omsdemo.interfaces.rest.v1.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OMSFilledOrderDTO {
    private Long id;
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private LocalDateTime filledDateTime;
}
