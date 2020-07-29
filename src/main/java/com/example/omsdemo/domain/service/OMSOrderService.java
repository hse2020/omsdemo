package com.example.omsdemo.domain.service;

import com.example.omsdemo.domain.model.OMSFilledOrder;
import com.example.omsdemo.domain.model.OMSPendingOrder;
import com.example.omsdemo.infrastructure.repository.OMSFilledOrderRepository;
import com.example.omsdemo.interfaces.rest.v1.factory.OMSOrderFactory;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OMSOrderService {

    @Autowired
    private OMSFilledOrderRepository filledOrderRepo;

    @Autowired
    private OMSOrderFactory factory;

    @Autowired
    private OMSOrderInventory inventory;

    // A robust order system would have considered synchronization when getting
    public void fulfill(OMSPendingOrder pendingOrder) {
        String symbol = pendingOrder.getSymbol();
        Set<OMSPendingOrder> inventoryOrders = inventory.getPendingOrdersBySymbol(symbol);

        Iterator<OMSPendingOrder> iterator = inventoryOrders.iterator();

        while (iterator.hasNext() && pendingOrder.isUnfilled()) {
            OMSPendingOrder inventoryOrder = iterator.next();

            if (!isOppositeOrderType(inventoryOrder, pendingOrder)
                    || !isSamePrice(inventoryOrder, pendingOrder)) {
                continue;
            }

            if (inventoryOrder.getQuantity().compareTo(pendingOrder.getQuantity()) > 0) {
                // inventory order size is larger than current order size
                book(pendingOrder);
                inventoryOrder.fillPartially(pendingOrder.getQuantity());
                pendingOrder.fillFully();
            } else if (inventoryOrder.getQuantity().compareTo(pendingOrder.getQuantity()) == 0) {
                // inventory order size is equal to current order size
                book(pendingOrder);
                inventoryOrder.fillFully();
                pendingOrder.fillFully();
            } else {
                // inventory order size is smaller than current order
                book(inventoryOrder);
                pendingOrder.fillPartially(inventoryOrder.getQuantity());
                inventoryOrder.fillFully();
            }
        }

        // Remove inventory orders that have been fully filled
        inventoryOrders = inventoryOrders.stream()
                .filter(OMSPendingOrder::isUnfilled)
                .collect(Collectors.toCollection(Sets::newLinkedHashSet));

        // Add pending order to inventory if not fully filled
        if (pendingOrder.isUnfilled()) {
            inventoryOrders.add(pendingOrder);
        }

        inventory.replaceValues(symbol, Sets.newLinkedHashSet(inventoryOrders));
    }

    public List<OMSFilledOrder> findAllFilledOrders() {
        return filledOrderRepo.findAll();
    }

    public Set<OMSPendingOrder> findAllPendingOrders(String symbol) {
        return inventory.getPendingOrdersBySymbol(symbol);
    }

    private void book(OMSPendingOrder pendingOrder) {
        filledOrderRepo.save(factory.buildFilledOrder(pendingOrder));
    }

    private boolean isOppositeOrderType(OMSPendingOrder o1, OMSPendingOrder o2) {
        return o1.getOrderType() != o2.getOrderType();
    }

    private boolean isSamePrice(OMSPendingOrder o1, OMSPendingOrder o2) {
        return o1.getPrice().compareTo(o2.getPrice()) == 0;
    }
}
