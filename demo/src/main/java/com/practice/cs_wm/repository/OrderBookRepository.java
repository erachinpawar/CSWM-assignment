package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.model.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {

	OrderBook findByInstrument(Instrument instrument);

}
