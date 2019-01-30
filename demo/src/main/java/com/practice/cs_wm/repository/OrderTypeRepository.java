package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.OrderType;

public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {

}
