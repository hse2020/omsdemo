package com.example.omsdemo.interfaces.rest.v1.dto;

import com.example.omsdemo.domain.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OMSPendingOrderDTO {
    @NonNull
    private String symbol;
    @NonNull
    private BigDecimal price;
    @NonNull
    private BigDecimal quantity;
    @NonNull
    private OrderType orderType;

    private LocalDateTime createdDateTime;
}