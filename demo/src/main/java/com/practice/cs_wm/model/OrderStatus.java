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
@Table(name = "Order_Status")
public class OrderStatus {

	@Id
	@Column(name = "order_status_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Status_seq")
	private long orderStatusId;

	@Column(name = "status_value")
	private String statusValue;

	@Column(name = "created_by")
	private String createdBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_on")
	private Date createdOn;


	
	
	public long getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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
		return "RefOrderStatus [orderStatusId=" + orderStatusId + ", statusValue=" + statusValue + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + "]";
	}

}
