package com.example.omsdemo.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(value = AccessLevel.NONE)
public class OMSFilledOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDateTime filledDateTime;
}
