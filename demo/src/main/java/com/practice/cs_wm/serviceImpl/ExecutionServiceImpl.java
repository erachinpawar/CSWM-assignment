package com.practice.cs_wm.serviceImpl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.repository.ExecutionRepository;
import com.practice.cs_wm.service.ExecutionService;
import com.practice.cs_wm.service.UserService;


@Service
@Transactional
public class ExecutionServiceImpl implements ExecutionService {
	
	@Autowired
	ExecutionRepository executionRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Execution getExecution() {
		
		return executionRepository.getOne(ApplicationConstants.EXECUTION_ID);
	}

	@Override
	public void addExecution(Execution execution) {

		executionRepository.save(execution);
	}

	@Override
	public Execution getTempExecutionForOrder(OrderBook orderBook) {
		
		Execution execution = new Execution();
		
		
		if(!orderBook.getExecutions().isEmpty())
		{
			Execution ordBookExec = orderBook.getExecutions().iterator().next();
			execution.setExecutionId(ordBookExec.getExecutionId());
			execution.setPrice(ordBookExec.getPrice());
			execution.setExecutionName(ordBookExec.getExecutionName());
		}
		execution.setCreatedBy(userService.getUserName());
		execution.setCreatedOn(new Date());
		execution.setOrderBook(orderBook);
		return execution;
	}

}
