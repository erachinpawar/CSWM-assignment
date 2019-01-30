package com.practice.cs_wm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderBookStatus;
import com.practice.cs_wm.repository.RefOrderBookStatusRepository;
import com.practice.cs_wm.service.RefOrderBookStatusService;

@Service
public class RefOrderBookStatusServiceImpl implements RefOrderBookStatusService {

	@Autowired
	RefOrderBookStatusRepository refOrderBookStatusRepository;

	@Override
	public List<OrderBookStatus> getAllOrderBookStatuses() {
		return (List<OrderBookStatus>) refOrderBookStatusRepository.findAll();
	}

	@Override
	public OrderBookStatus addOrderBookStatus(OrderBookStatus refOrderBookStatus) {
		return refOrderBookStatusRepository.save(refOrderBookStatus);
	}

	@Override
	public OrderBookStatus getOrderBookStatus(long orderBookStatusId) {
		return refOrderBookStatusRepository.findByOrderBookStatusId(orderBookStatusId);
	}

	@Override
	public OrderBookStatus updateOrderBookStatus(OrderBookStatus refOrderBookStatus) {
		return refOrderBookStatusRepository.save(refOrderBookStatus);
	}

	@Override
	public void removeOrderBookStatus(long orderBookStatusId) {
		refOrderBookStatusRepository.deleteById(orderBookStatusId);

	}

	@Override
	public Map<Long, OrderBookStatus> getBookStatusMap() {
		Map<Long, OrderBookStatus> orderBookStatusMap = new HashMap<Long, OrderBookStatus>();
		getAllOrderBookStatuses().forEach(
				orderBookStatus -> orderBookStatusMap.put(orderBookStatus.getOrderBookStatusId(), orderBookStatus));
		return orderBookStatusMap;
	}

}
