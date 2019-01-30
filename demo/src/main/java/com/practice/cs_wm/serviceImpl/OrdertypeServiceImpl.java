package com.practice.cs_wm.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.OrderType;
import com.practice.cs_wm.repository.OrderTypeRepository;
import com.practice.cs_wm.service.OrderTypeService;

@Service
@Transactional
public class OrdertypeServiceImpl implements OrderTypeService {

	@Autowired
	OrderTypeRepository orderTypeRepository;

	@Override
	public OrderType getOrderTypeById(long orderTypeId) {
		return orderTypeRepository.getOne(orderTypeId);
	}

	public Map<Long, OrderType> getAllModelTypes() {
		Map<Long, OrderType> orderTypeMap = new HashMap<Long, OrderType>();
		orderTypeRepository.findAll().forEach(orderType -> orderTypeMap.put(orderType.getOrderTypeId(), orderType));
		return orderTypeMap;
	}

}
