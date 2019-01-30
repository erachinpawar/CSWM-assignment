package com.practice.cs_wm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderStatus;
import com.practice.cs_wm.repository.RefOrderStatusRepository;
import com.practice.cs_wm.service.RefOrderStatusService;

@Service
public class RefOrderStatusServiceImpl implements RefOrderStatusService  {

	@Autowired
	RefOrderStatusRepository refOrderStatusRepository;

	@Override
	public List<OrderStatus> getAllOrderStatuses() {
		return refOrderStatusRepository.findAll();
	}

	@Override
	public OrderStatus addOrderStatus(OrderStatus refOrderStatus) {
		return refOrderStatusRepository.save(refOrderStatus);
	}

	@Override
	public OrderStatus getOrderStatus(long orderStatusId) {
		return refOrderStatusRepository.getOne(orderStatusId);
	}

	@Override
	public OrderStatus updateOrderStatus(OrderStatus refOrderStatus) {
		return refOrderStatusRepository.save(refOrderStatus);
	}

	@Override
	public void removeOrderStatus(long orderStatusId) {
		
		refOrderStatusRepository.deleteById(orderStatusId);
	}
}
