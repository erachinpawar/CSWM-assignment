package com.practice.cs_wm.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.modelVos.OrderStatsVo;

@Service
public interface OrderService  extends AbstractService {

	List<Order> getAllOrders();

	Order addOrder(Order order);

	Order getOrder(long orderId);

	Order updateOrder(Order order);

	void removeOrder(long orderId);

	// void updateOrdersForExecutions(Instrument instrument, long quantity);

	List<Order> getValidOrders(OrderBook orderBook);

	int getTotalExecQtyForBook(OrderBook orderBook);

	int getAccOrdersFromValidOrders(List<Order> validOrders);

	double getTotExecQtyValidOrders(List<Order> validOrders);

	void addExecutionToOrders(List<Order> validOrders, double perOrderExec);

	Set<Order> getOrdersForBook(OrderBook orderBook);

	Order getBiggestOrderForBook(OrderBook orderBook);

	Order getSmallestOrderForBook(OrderBook orderBook);

	Order getEarliestOrderInBook(OrderBook orderBook);

	Order getLatestOrderInBook(OrderBook orderBook);

	OrderStatsVo getOrderStats(long orderId);

	List<Order> getOrphanOrders(Instrument instrument);

	void UpdateOrderBookForOrder(OrderBook orderBook, long orderId);

	Order createDefaultOrder();

	OrderStatsVo getOrderBookStats(long orderId);

}
