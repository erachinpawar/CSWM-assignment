package com.practice.cs_wm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderStatus;

@Service
public interface RefOrderStatusService  extends AbstractService {


	List<OrderStatus> getAllOrderStatuses();

	OrderStatus addOrderStatus(OrderStatus refOrderStatus);

	OrderStatus getOrderStatus(long orderStatusId);

	OrderStatus updateOrderStatus(OrderStatus refOrderStatus);

	void removeOrderStatus(long orderStatusId);


}
