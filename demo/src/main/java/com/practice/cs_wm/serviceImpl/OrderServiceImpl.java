package com.practice.cs_wm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.modelVos.OrderStatsVo;
import com.practice.cs_wm.repository.OrderRepository;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderBookService;
import com.practice.cs_wm.service.OrderService;
import com.practice.cs_wm.service.OrderTypeService;
import com.practice.cs_wm.service.UserService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ServiceFactory serviceFactory;
	
	
	@Override
	public List<Order> getAllOrders() {
		return (List<Order>) orderRepository.findAll();
	}

	@Override
	public Order addOrder(Order order) {

		if (null != order) {
			if (null != order.getInstrument()) {
				
				order.setInstrument(((InstrumentService) serviceFactory.getService(ApplicationConstants.INSTRUMENT_SERVICE)).getInstrument(order.getInstrument().getRefInstrumentId()));
				if(null!=order.getOrderType())
				{
					order.setOrderType(((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getOrderTypeById(order.getOrderType().getOrderTypeId()));
					order = modifyOrderAmoutIfLimitOrder(order);
				}
				else
				{
					// throw exception that order type can not ve null
				}
			} else {
				// throw exception that instrument cannot be null
			}
		}
		order.setExecutionQuantity(0f);
		// many to one not supporting H2 Db hence made provision with default order
		if(null==order.getOrderBook())
		{
			saveOrderForNullBook(order);
		}
		else
		{
			orderRepository.save(order);
		}
		
		return order;
	}

	private void saveOrderForNullBook(Order order) {
		order.setOrderBook(((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(ApplicationConstants.DEFAULT_ORDER_BOOK));
		orderRepository.save(order);
		orderRepository.removeOrderfromBook(order.getOrderId());
		
	}

	private Order modifyOrderAmoutIfLimitOrder(Order order) {
		if (ApplicationConstants.ORDER_TYPE_LIMIT == order.getOrderType().getOrderTypeId()) {
			order.setOrderprice(order.getOrderType().getValue());
		}
		return order;
	}

	@Override
	public Order getOrder(long orderId) {
		return orderRepository.getOne(orderId);
	}

	@Override
	public Order updateOrder(Order order) {
		if (null != order && null == order.getOrderBook()) {
			{
				order = modifyOrderAmoutIfLimitOrder(order);
				orderRepository.save(order);
			}
		}

		return order;
	}

	@Override
	public void removeOrder(long orderId) {
		orderRepository.deleteById(orderId);
	}

//	@Override
//	public void updateOrdersForExecutions(Instrument instrument, long quantity) {
//
//		Execution execution = executionService.getExecution();
//		List<Order> validOrders = getValidOrders(execution.getPrice(), instrument);
//		float perOrderExecution = StatsUtil.getlinearExecutionPerOrder(validOrders.size(), quantity);
//
//		validOrders.forEach(order -> {
//			order.setExecutionQuantity(order.getExecutionQuantity() + perOrderExecution);
//			orderRepository.save(order);
//		});
//
//	}

	@Override
	public List<Order> getValidOrders(OrderBook orderBook) {
		
		Set<Order> allOrdersForBook = orderRepository.findAllByOrderBook(orderBook);
		long executionPrice =orderBook.getExecutions().iterator().next().getPrice();
		
		List<Order> validOrders = new ArrayList<Order>();
		
		allOrdersForBook.forEach(order -> {
			if ((order.getOrderprice() < executionPrice && order.getOrderType().equals(((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getOrderTypeById(ApplicationConstants.ORDER_TYPE_LIMIT)))
					|| order.getOrderType().equals(((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getOrderTypeById(ApplicationConstants.ORDER_TYPE_MARKET))) {
				validOrders.add(order);
			}
		});
		
		/*
		 * List<Order> marketOrders =
		 * orderRepository.findAllByOrderTypeAndOrderBook(orderTypeService.
		 * getOrderTypeById(ApplicationConstants.ORDER_TYPE_MARKET),orderBook);
		 * 
		 * List<Order> validLimitOrders =
		 * orderRepository.findAllByOrderTypeAndOrderBookAndOrderpriceLessThan(
		 * orderTypeService.getOrderTypeById(ApplicationConstants.ORDER_TYPE_LIMIT),
		 * orderBook,orderBook.getExecutions().iterator().next().getPrice());
		 * 
		 * validLimitOrders.addAll(marketOrders);
		 */

		return validOrders;
	}

	@Override
	public int getTotalExecQtyForBook(OrderBook orderBook) {
		int sumOfExecQtyForBook = orderRepository.getExecQtyForINstr(orderBook.getInstrument());
		return sumOfExecQtyForBook;
	}

	@Override
	public int getAccOrdersFromValidOrders(List<Order> validOrders) {
		int accOrderQuant = 0;
		for (Order order : validOrders) {
			accOrderQuant=accOrderQuant+order.getOrderQuantity();
		}
		
		return accOrderQuant;
				//validOrders.stream().mapToInt(o -> o.getOrderQuantity()).sum();
	}

	@Override
	public double getTotExecQtyValidOrders(List<Order> validOrders) {
		
		double totExecQtyValidOrder = 0;
		for (Order order : validOrders) {
			totExecQtyValidOrder+=(null==order.getExecutionQuantity()?0:order.getExecutionQuantity());
		}
		
		return totExecQtyValidOrder;
				//validOrders.stream().mapToDouble(o -> o.getExecutionQuantity()).sum();
	}

	@Override
	public void addExecutionToOrders(List<Order> validOrders, double perOrderExec) {
		for (Order order : validOrders) {
			order.setExecutionQuantity(
					(float) ((null==order.getExecutionQuantity()?0:order.getExecutionQuantity()) + (order.getOrderQuantity() * perOrderExec)));
		}
		orderRepository.saveAll(validOrders);
	}

	@Override
	public Set<Order> getOrdersForBook(OrderBook orderBook) {
		Set<Order> orders = orderRepository.findAllByOrderBook(orderBook);
		return orders;
	}

	@Override
	public Order getBiggestOrderForBook(OrderBook orderBook) {

		return orderRepository.findFirstByOrderBookOrderByOrderQuantityDesc(orderBook);
	}

	@Override
	public Order getSmallestOrderForBook(OrderBook orderBook) {
		return orderRepository.findFirstByOrderBookOrderByOrderQuantityAsc(orderBook);
	}

	@Override
	public Order getEarliestOrderInBook(OrderBook orderBook) {
		return orderRepository.findFirstByOrderBookOrderByCreatedOnAsc(orderBook);
	}

	@Override
	public Order getLatestOrderInBook(OrderBook orderBook) {
		return orderRepository.findFirstByOrderBookOrderByCreatedOnDesc(orderBook);
	}

	@Override
	public OrderStatsVo getOrderStats(long orderId) {
		OrderStatsVo orderStatsVo = new OrderStatsVo();
		orderStatsVo.setOrder(getOrder(orderId));
		orderStatsVo
				.setExecutionPrice(orderStatsVo.getOrder().getOrderBook().getExecutions().iterator().next().getPrice());
		return null;
	}

	@Override
	public List<Order> getOrphanOrders(Instrument instrument) {
		return orderRepository.findAllByInstrumentAndOrderBookIsNull(instrument);
	}

	@Override
	public void UpdateOrderBookForOrder(OrderBook orderBook, long orderId) {
		Order order = getOrder(orderId);
		order.setOrderBook(orderBook);
		orderRepository.save(order);
	}

	@Override
	public Order createDefaultOrder() {
		Order order = new Order();
		order.setCreatedBy(((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		order.setCreatedOn(new Date());
		order.setExecutionQuantity(0f);
		order.setOrderprice(0f);
		order.setOrderQuantity(0);
		order.setOrderType(((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getOrderTypeById(ApplicationConstants.ORDER_TYPE_LIMIT));
		return order;
	}

	@Override
	public OrderStatsVo getOrderBookStats(long orderId) {
		OrderStatsVo orderStatsVo = new OrderStatsVo();
		orderStatsVo.setOrder(getOrder(orderId));
		orderStatsVo.setOrderStatus(getOrderStatus(orderStatsVo.getOrder()));
		orderStatsVo.setExecutionPrice(getTotalExecutionPrice(orderStatsVo.getOrder()));
		return orderStatsVo;
	}

	private double getTotalExecutionPrice(Order order) {
		
		if (null == order.getOrderBook() || order.getOrderBook().getExecutions().isEmpty()) {
			return 0;
		}
		else {
			Execution execution = order.getOrderBook().getExecutions().iterator().next();
			return (null==order.getExecutionQuantity()?0:order.getExecutionQuantity())
					*execution.getPrice();
		}
	}

	private String getOrderStatus(Order order) {

		if (null == order.getOrderBook() || order.getOrderBook().getExecutions().isEmpty()) {
			return ApplicationConstants.ORDER_STATUS_NOT_SET;
		} else if (ApplicationConstants.ORDER_TYPE_MARKET == order.getOrderType().getOrderTypeId()) {
			return ApplicationConstants.ORDER_STATUS_VALID;
		} else {
			Execution execution = order.getOrderBook().getExecutions().iterator().next();
			if (order.getOrderprice() < execution.getPrice()) {
				return ApplicationConstants.ORDER_STATUS_INVALID;
			}
		}
		return ApplicationConstants.ORDER_STATUS_VALID;
	}
	
	

}
