package com.practice.cs_wm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderBookStatus;

@Service
public interface RefOrderBookStatusService {

	void removeOrderBookStatus(long refOrderBookStatusId);

	List<OrderBookStatus> getAllOrderBookStatuses();

	OrderBookStatus addOrderBookStatus(OrderBookStatus refOrderBookStatus);

	OrderBookStatus getOrderBookStatus(long orderBookStatusId);

	OrderBookStatus updateOrderBookStatus(OrderBookStatus refOrderBookStatus);

	Map<Long, OrderBookStatus> getBookStatusMap();


}
