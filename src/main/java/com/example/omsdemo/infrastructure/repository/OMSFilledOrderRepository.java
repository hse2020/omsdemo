package com.example.omsdemo.infrastructure.repository;

import com.example.omsdemo.domain.model.OMSFilledOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OMSFilledOrderRepository extends JpaRepository<OMSFilledOrder, Long> {
}
