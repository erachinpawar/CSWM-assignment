package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.OrderBookStatus;

public interface RefOrderBookStatusRepository  extends JpaRepository<OrderBookStatus, Long>{

	OrderBookStatus findByOrderBookStatusId(long refBookStatusId);

	void deleteByOrderBookStatusId(long refOrderBookStatusId);

}
