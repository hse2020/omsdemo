package com.example.omsdemo.domain.service;

import com.example.omsdemo.domain.model.OMSPendingOrder;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
class OMSOrderInventory {
    private Multimap<String, OMSPendingOrder> _cache = Multimaps.synchronizedSetMultimap(LinkedHashMultimap.create());

    Set<OMSPendingOrder> getPendingOrdersBySymbol(String symbol) {
        return Sets.newLinkedHashSet(_cache.get(symbol));
    }

    void replaceValues(String symbol, LinkedHashSet<OMSPendingOrder> orders) {
        _cache.replaceValues(symbol, orders);
    }
}
