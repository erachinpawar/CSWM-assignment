package com.practice.cs_wm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.modelVos.OrderBookStatsVo;

@Service
public interface OrderBookService  extends AbstractService {

	List<OrderBook> getAllOrderBooks();

	OrderBook addOrderBook(OrderBook orderBook);

	OrderBook getOrderBook(long orderBookId);

	OrderBook updateOrderBook(OrderBook orderBook);

	void removeOrderBook(long orderBookId);

	OrderBook openCloseOrderBook(long orderBookId, long orderBookStatus);


	OrderBookStatsVo getOrderBookStats(long orderBookId);

	OrderBook createDefaultOrderBook();

	List<Order> getOrphanOrders(OrderBook orderBook);

	OrderBook acceptOrderForOrderBook(long orderBookId, long orderId);

	OrderBook addExecution(Execution execution);

}
