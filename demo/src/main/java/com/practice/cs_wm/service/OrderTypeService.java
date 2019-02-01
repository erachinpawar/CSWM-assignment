package com.practice.cs_wm.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderType;

@Service
@Transactional
public interface OrderTypeService  extends AbstractService {
	public OrderType getOrderTypeById(long orderTypeId);

	public Map<Long, OrderType> getAllModelTypes();
}
