package com.practice.cs_wm.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.OrderBook;

@Service
@Transactional
public interface ExecutionService {
	
	public Execution getExecution();

	public void addExecution(Execution execution);

	public Execution getTempExecutionForOrder(OrderBook orderBook);


}
