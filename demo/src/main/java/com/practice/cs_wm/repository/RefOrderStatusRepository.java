package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.OrderStatus;


public interface RefOrderStatusRepository extends JpaRepository<OrderStatus, Long> {

	OrderStatus findByOrderStatusId(long orderStatusId);

	void deleteByOrderStatusId(long refOrderStatusId);

}
