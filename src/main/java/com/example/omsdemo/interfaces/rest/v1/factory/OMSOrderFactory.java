package com.example.omsdemo.interfaces.rest.v1.factory;

import com.example.omsdemo.domain.model.OMSFilledOrder;
import com.example.omsdemo.domain.model.OMSPendingOrder;
import com.example.omsdemo.interfaces.rest.v1.dto.OMSFilledOrderDTO;
import com.example.omsdemo.interfaces.rest.v1.dto.OMSPendingOrderDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OMSOrderFactory {
    public OMSPendingOrder buildPendingOrder(OMSPendingOrderDTO dto) {
        if (dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (dto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return OMSPendingOrder.builder()
                .symbol(dto.getSymbol())
                .orderType(dto.getOrderType())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .createdDateTime(LocalDateTime.now())
                .build();
    }

    public OMSFilledOrder buildFilledOrder(OMSPendingOrder pendingOrder) {
        return OMSFilledOrder.builder()
                .price(pendingOrder.getPrice())
                .quantity(pendingOrder.getQuantity())
                .symbol(pendingOrder.getSymbol())
                .filledDateTime(LocalDateTime.now())
                .build();
    }

    public OMSFilledOrderDTO buildFilledOrderDto(OMSFilledOrder entity) {
        return OMSFilledOrderDTO.builder()
                .id(entity.getId())
                .symbol(entity.getSymbol())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .filledDateTime(entity.getFilledDateTime())
                .build();
    }

    public OMSPendingOrderDTO buildPendingOrderDto(OMSPendingOrder entity) {
        return OMSPendingOrderDTO.builder()
                .symbol(entity.getSymbol())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .orderType(entity.getOrderType())
                .createdDateTime(entity.getCreatedDateTime())
                .build();
    }
}
