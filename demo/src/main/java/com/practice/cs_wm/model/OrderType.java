package com.practice.cs_wm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Order_Type")
public class OrderType {
	@Id
	@Column(name = "order_Type_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Type_seq")
	private long orderTypeId;
	
	@Column(name="order_type")
	private String orderType;
	
	@Column(name="limit_value")
	private Float value;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_on")
	private Date createdOn;
	
	public long getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "OrderType [orderTypeId=" + orderTypeId + ", orderType=" + orderType + ", value=" + value
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	
	


}
