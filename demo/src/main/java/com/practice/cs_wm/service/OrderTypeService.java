package com.practice.cs_wm.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderType;

@Service
@Transactional
public interface OrderTypeService {
	public OrderType getOrderTypeById(long orderTypeId);

	public Map<Long, OrderType> getAllModelTypes();
}
