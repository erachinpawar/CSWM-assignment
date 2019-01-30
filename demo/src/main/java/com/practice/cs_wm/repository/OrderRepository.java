package com.practice.cs_wm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.model.OrderType;

public interface OrderRepository  extends JpaRepository<Order, Long>{

	@Query("select c from Order c where c.orderBook = :orderBook AND (c.orderType = :marketOrderType OR (c.orderType = :limitOrderType and c.orderprice < :price))")
	List<Order> getValidOrders(OrderBook orderBook, long price, OrderType limitOrderType, OrderType marketOrderType);

	@Query("select sum(c.executionQuantity) from Order c where c.instrument =:instrument")
	int getExecQtyForINstr(Instrument instrument);

	Set<Order> findAllByOrderBook(OrderBook orderBook);

	Order findFirstByOrderBookOrderByOrderQuantityDesc(OrderBook orderBook);

	Order findFirstByOrderBookOrderByOrderQuantityAsc(OrderBook orderBook);

	Order findFirstByOrderBookOrderByCreatedOnAsc(OrderBook orderBook);

	Order findFirstByOrderBookOrderByCreatedOnDesc(OrderBook orderBook);

	List<Order> findAllByInstrumentAndOrderBookIsNull(Instrument instrument);

	List<Order> findAllByOrderTypeAndOrderBook(OrderType orderTypeById, OrderBook orderBook);

	List<Order> findAllByOrderTypeAndOrderBookAndOrderpriceLessThan(OrderType orderTypeById, OrderBook orderBook,
			long price);

	@Modifying
	@Query(value ="update orders_INV set order_book_id = null where order_id=:orderId", nativeQuery = true)
	void removeOrderfromBook(long orderId);



	
}
