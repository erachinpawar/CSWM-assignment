package com.practice.cs_wm.modelVos;

import com.practice.cs_wm.model.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatsVo {


	private Order order;
	private double executionPrice;
	private String orderStatus;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "OrderStatsVo [order=" + order + ", executionPrice=" + executionPrice + ", orderStatus=" + orderStatus
				+ "]";
	}
	
	
	
	
	
}
