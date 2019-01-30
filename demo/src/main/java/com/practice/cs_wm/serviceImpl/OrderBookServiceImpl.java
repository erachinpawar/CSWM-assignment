package com.practice.cs_wm.serviceImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.Util.StatsUtil;
import com.practice.cs_wm.exceptions.DataNotFoundException;
import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.modelVos.OrderBookStatsVo;
import com.practice.cs_wm.repository.OrderBookRepository;
import com.practice.cs_wm.service.ExecutionService;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderBookService;
import com.practice.cs_wm.service.OrderService;
import com.practice.cs_wm.service.RefOrderBookStatusService;
import com.practice.cs_wm.service.UserService;

@Service
@Transactional
public class OrderBookServiceImpl implements OrderBookService {

	@Autowired
	OrderBookRepository orderBookRepository;

	@Autowired
	RefOrderBookStatusService refOrderBookStatusService;

	@Autowired
	OrderService orderService;

	@Autowired
	ExecutionService executionService;
	
	@Autowired
	InstrumentService instrumentService;
	@Autowired
	private UserService userService;

	@Override
	public List<OrderBook> getAllOrderBooks() {
		return (List<OrderBook>) orderBookRepository.findAll();
	}

	@Override
	public OrderBook addOrderBook(OrderBook orderBook) {
		validate(orderBook);
		orderBook.setOrderBookStatus(refOrderBookStatusService.getOrderBookStatus(orderBook.getOrderBookStatus().getOrderBookStatusId()));
		if(null!=orderBook.getInstrument())
		orderBook.setInstrument(instrumentService.getInstrument(orderBook.getInstrument().getRefInstrumentId()));
		if(null==orderBook.getExecutionStatus())
			orderBook.setBookExecutionStatus(ApplicationConstants.BOOK_EXECUTED_NO);
			orderBookRepository.save(orderBook);
		return orderBook;
	}

	private void validate(OrderBook orderBook) {
		if (null == orderBook.getOrderBookName()||orderBook.getOrderBookName().isEmpty()) {
			throw new DataNotFoundException("order can not be created without book name");
		}
	}

	@Override
	public OrderBook getOrderBook(long orderBookId) {
		return  orderBookRepository.getOne(orderBookId);
	}

	@Override
	public OrderBook updateOrderBook(OrderBook orderBook) {
		return orderBookRepository.save(orderBook);
	}

	@Override
	public void removeOrderBook(long orderBookId) {
		orderBookRepository.deleteById(orderBookId);
	}

	@Override
	public OrderBook openCloseOrderBook(long orderBookId, long orderBookStatus) {
		OrderBook orderBook = getOrderBook(orderBookId);
		orderBook.setOrderBookStatus(refOrderBookStatusService.getOrderBookStatus(orderBookStatus));
		orderBookRepository.save(orderBook);
		return orderBook;
	}



	private boolean isOrderBookEligibleForExec(OrderBook orderBook, Execution execution) {
		if (ApplicationConstants.BOOK_EXECUTED_YES.equalsIgnoreCase(orderBook.getBookExecutionStatus()))
			return false;

		List<Order> validOrders = orderService.getValidOrders(orderBook);
		
		int accumltdOrders = orderService.getAccOrdersFromValidOrders(validOrders);
		double accExecQty=orderService.getTotExecQtyValidOrders(validOrders);
		if((double)accumltdOrders<=accExecQty)
		{
			orderBook.setBookExecutionStatus(ApplicationConstants.BOOK_EXECUTED_YES);
			updateOrderBook(orderBook);
			return false;
		}
		else
		{
			double effectiveQuanty = StatsUtil.getEffectiveQtyForExec(accumltdOrders,accExecQty,execution.getQuantity());
			double perOrderExec;
			if (effectiveQuanty!=execution.getQuantity())
			{
				// log the differene between the execution.getQuantity() - getEffectiveQuanty
			}
				
				perOrderExec = StatsUtil.getlinearExecutionPerOrder(accumltdOrders,(long) effectiveQuanty);
				
			orderService.addExecutionToOrders(validOrders,perOrderExec);
			execution.setQuantity((long) effectiveQuanty);
			return true;
		}
	}

	private boolean orderBookClosed(OrderBook orderBook) {
		if (ApplicationConstants.ORDER_BOOK_STATUS_CLOSE == orderBook.getOrderBookStatus().getOrderBookStatusId())
			return true;
		return false;
	}

	@Override
	public OrderBook addExecution( Execution execution) {
		OrderBook orderBook = getOrderBook(execution.getOrderBook().getOrderBookId());

		if (orderBookClosed(orderBook) == false) {
			// throw exception that book is open
		}

		else {
			if (isOrderBookEligibleForExec(orderBook,execution)) {
				execution.setOrderBook(orderBook);
				executionService.addExecution(execution);
			}
			else
			{
				// throw exception that order Book is not eligible for the execution
			}

		}

		return null;
	}

	@Override
	public OrderBookStatsVo getOrderBookStats(long orderBookId) {
		OrderBook orderBook =getOrderBook(orderBookId);
		List<Order> validOrders =orderService.getValidOrders(orderBook);
		List<Order> bookOrders= new ArrayList<>(orderService.getOrdersForBook(orderBook));
		List<Order> invalidOrders = new ArrayList<>(bookOrders);
		invalidOrders.removeAll(validOrders);
		OrderBookStatsVo orderBookStatsVo = new OrderBookStatsVo()
				.setOrderBook(orderBook)
				.setTotalNoOfOrders(bookOrders.size())
				.setTotalNoofAccuOrders(orderService.getAccOrdersFromValidOrders(bookOrders))
				.setOrderStats(getStatsMapforOrderBook(orderBook))
				.setValidOrderCount(validOrders.size())
				.setInValidOrderCount(bookOrders.size()-validOrders.size())
				.setValidDemand(orderService.getAccOrdersFromValidOrders(validOrders))
				.setInvalidDemand(orderService.getAccOrdersFromValidOrders(invalidOrders))
				.setExecutionQty(orderService.getTotExecQtyValidOrders(validOrders));
		orderBookStatsVo.setExecutionPrice(orderBookStatsVo.getExecutionQty()*orderBook.getExecutions().iterator().next().getPrice());
			
		
		return orderBookStatsVo;
	}
	
	

	private Map<String, Order> getStatsMapforOrderBook(OrderBook orderBook) {
		Map<String, Order> orderStats = new HashMap<>();
		orderStats.put(ApplicationConstants.BIGGEST_ORDER,orderService.getBiggestOrderForBook(orderBook));
		orderStats.put(ApplicationConstants.SMALLEST_ORDER,orderService.getSmallestOrderForBook(orderBook));
		orderStats.put(ApplicationConstants.EARLIEST_ORDER,orderService.getEarliestOrderInBook(orderBook));
		orderStats.put(ApplicationConstants.LATEST_ORDER,orderService.getLatestOrderInBook(orderBook));
		
		return orderStats;
	}

	@Override
	public OrderBook createDefaultOrderBook() {
		OrderBook orderBook=new OrderBook();
		orderBook.setCreatedBy(userService.getUserName());
		orderBook.setCreatedOn(new Date());
		orderBook.setBookExecutionStatus("N");
		orderBook.setOrderBookStatus(refOrderBookStatusService.getOrderBookStatus(ApplicationConstants.ORDER_BOOK_STATUS_OPEN));
		return orderBook;
	}

	@Override
	public List<Order> getOrphanOrders(OrderBook orderBook) {
		return orderService.getOrphanOrders(orderBook.getInstrument());
	}

	@Override
	public OrderBook acceptOrderForOrderBook(long orderBookId, long orderId) {
		OrderBook orderBook = getOrderBook(orderBookId);
		orderService.UpdateOrderBookForOrder(orderBook,orderId);
		return orderBook;
	}


	



}
