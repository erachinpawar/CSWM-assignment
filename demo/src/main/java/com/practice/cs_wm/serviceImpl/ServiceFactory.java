package com.practice.cs_wm.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.service.AbstractService;
import com.practice.cs_wm.service.ExecutionService;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderBookService;
import com.practice.cs_wm.service.OrderService;
import com.practice.cs_wm.service.OrderTypeService;
import com.practice.cs_wm.service.RefOrderBookStatusService;
import com.practice.cs_wm.service.RefOrderStatusService;
import com.practice.cs_wm.service.UserService;

@Service
@Transactional
public class ServiceFactory {

	@Autowired
	private OrderBookService orderBookService;
	
	@Autowired
	OrderService orderService;

	
	@Autowired
	private InstrumentService instrumentService;
	
	@Autowired
	private ExecutionService executionService;

	@Autowired
	private UserService userService;
	
	@Autowired
	OrderTypeService orderTypeService;
	
	@Autowired
	RefOrderBookStatusService refOrderBookStatusService;
	
	@Autowired
	RefOrderStatusService refOrderStatusService;

	public AbstractService getService(String service) {
		if (service == null) {
			return null;
		}
		if (ApplicationConstants.ORDER_SERVICE.equalsIgnoreCase(service)) {
			return orderService;

		} else if (ApplicationConstants.ORDER_BOOK_SERVICE.equalsIgnoreCase(service)) {
			return orderBookService;
		}else if (ApplicationConstants.INSTRUMENT_SERVICE.equalsIgnoreCase(service)) {
			return instrumentService;
		}else if (ApplicationConstants.EXECUTION_SERVICE.equalsIgnoreCase(service)) {
			return executionService;
		}else if (ApplicationConstants.USER_SERVICE.equalsIgnoreCase(service)) {
			return userService;
		}else if (ApplicationConstants.REF_ORDER_BOOK_STATUS_SERVICE.equalsIgnoreCase(service)) {
			return refOrderBookStatusService;
		}else if (ApplicationConstants.REF_ORDER_STATUS_SERVICE.equalsIgnoreCase(service)) {
			return refOrderStatusService;
		}
		else if (ApplicationConstants.REF_ORDER_TYPE_SERVICE.equalsIgnoreCase(service)) {
			return orderTypeService;
		}
		return null;
	}

}
