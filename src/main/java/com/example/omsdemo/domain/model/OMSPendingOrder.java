package com.example.omsdemo.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(value = AccessLevel.NONE)
public class OMSPendingOrder {
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private OrderType orderType;
    private LocalDateTime createdDateTime;

    public void fillPartially(BigDecimal qty) {
        quantity = quantity.add(qty.negate());
    }

    public void fillFully() {
        quantity = BigDecimal.ZERO;
    }

    public boolean isUnfilled() {
        return quantity.compareTo(BigDecimal.ZERO) != 0;
    }
}
