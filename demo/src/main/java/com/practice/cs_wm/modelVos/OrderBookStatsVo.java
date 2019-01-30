package com.practice.cs_wm.modelVos;

import java.util.Map;

import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookStatsVo {
	
	private int totalNoOfOrders;
	private int totalNoofAccuOrders;
//	private Order biggestOrder;
//	private Order smallestOrder;
//	private Order earliestOrder;
//	private Order latestOrder;
	
	private Map<String, Order> orderStats;
	private int validOrderCount;
	private int inValidOrderCount;
	private int validDemand;
	private int invalidDemand;
	private double executionQty;
	private double executionPrice;
	
	private OrderBook orderBook;
	
	public int getTotalNoOfOrders() {
		return totalNoOfOrders;
	}
	public OrderBookStatsVo setTotalNoOfOrders(int totalNoOfOrders) {
		this.totalNoOfOrders = totalNoOfOrders;
		return this;
	}
	public int getTotalNoofAccuOrders() {
		return totalNoofAccuOrders;
	}
	public OrderBookStatsVo setTotalNoofAccuOrders(int totalNoofAccuOrders) {
		this.totalNoofAccuOrders = totalNoofAccuOrders;
		return this;
	}
	public int getValidOrderCount() {
		return validOrderCount;
	}
	public OrderBookStatsVo setValidOrderCount(int validOrderCount) {
		this.validOrderCount = validOrderCount;
		return this;
	}
	public int getInValidOrderCount() {
		return inValidOrderCount;
	}
	public OrderBookStatsVo setInValidOrderCount(int inValidOrderCount) {
		this.inValidOrderCount = inValidOrderCount;
		return this;
	}
	public int getValidDemand() {
		return validDemand;
	}
	public OrderBookStatsVo setValidDemand(int validDemand) {
		this.validDemand = validDemand;
		return this;
	}
	public int getInvalidDemand() {
		return invalidDemand;
	}
	public OrderBookStatsVo setInvalidDemand(int invalidDemand) {
		this.invalidDemand = invalidDemand;
		return this;
	}
	public double getExecutionQty() {
		return executionQty;
	}
	public OrderBookStatsVo setExecutionQty(double executionQty) {
		this.executionQty = executionQty;
		return this;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	public OrderBookStatsVo setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
		return this;
	}
	
	
	public OrderBook getOrderBook() {
		return orderBook;
	}
	public OrderBookStatsVo setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
		return this;
	}
	public Map<String, Order> getOrderStats() {
		return orderStats;
	}
	public OrderBookStatsVo setOrderStats(Map<String, Order> orderStats) {
		this.orderStats = orderStats;
		return this;
	}
	@Override
	public String toString() {
		return "OrderBookStatsVo [totalNoOfOrders=" + totalNoOfOrders + ", totalNoofAccuOrders=" + totalNoofAccuOrders
				+ ", orderStats=" + orderStats + ", validOrderCount=" + validOrderCount + ", inValidOrderCount="
				+ inValidOrderCount + ", validDemand=" + validDemand + ", invalidDemand=" + invalidDemand
				+ ", executionQty=" + executionQty + ", executionPrice=" + executionPrice + ", orderBook=" + orderBook
				+ "]";
	}
	
	
	
	
	
}
