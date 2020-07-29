package com.example.omsdemo.interfaces.rest.v1;

import com.example.omsdemo.domain.model.OMSPendingOrder;
import com.example.omsdemo.domain.service.OMSOrderService;
import com.example.omsdemo.interfaces.rest.v1.dto.OMSPendingOrderDTO;
import com.example.omsdemo.interfaces.rest.v1.dto.OMSFilledOrderDTO;
import com.example.omsdemo.interfaces.rest.v1.factory.OMSOrderFactory;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OMSOrderController {

    @Autowired
    private OMSOrderFactory factory;

    @Autowired
    private OMSOrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OMSPendingOrderDTO dto) {
        orderService.fulfill(factory.buildPendingOrder(dto));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filled")
    public List<OMSFilledOrderDTO> getAllFilledOrders() {
        return orderService.findAllFilledOrders().stream()
                .map(factory::buildFilledOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/pending")
    public Set<OMSPendingOrderDTO> getAllPendingOrders(@RequestParam("symbol") String symbol) {
        return orderService.findAllPendingOrders(symbol).stream()
                .map(factory::buildPendingOrderDto)
                .collect(Collectors.toCollection(Sets::newLinkedHashSet));
    }
}
