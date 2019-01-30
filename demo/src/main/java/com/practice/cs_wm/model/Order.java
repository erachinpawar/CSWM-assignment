package com.practice.cs_wm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "orders_INV")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_INV_seq")
	private long orderId;

	@Column(name = "order_name")
	private String orderName;

	@OneToOne
	@JoinColumn(name = "instrument_id")
	private Instrument instrument;

	@Column(name = "order_quantity")
	private int orderQuantity;

	@Column(name = "order_price")
	private Float orderprice;

	@OneToOne(optional=true)
	@JoinColumn(name = "order_status_id", nullable=true)
	private OrderStatus orderStatus;

	@OneToOne
	@JoinColumn(name = "order_type_id")
	private OrderType orderType;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true,cascade = CascadeType.ALL)
	@JoinColumn(name = "order_book_id", nullable = true)
	 @JsonBackReference
	private OrderBook orderBook;

	@Column(name = "execution_quantity")
	private Float executionQuantity;

	@Column(name = "created_by")
	private String createdBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_on")
	private Date createdOn;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Float getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(Float orderprice) {
		this.orderprice = orderprice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Float getExecutionQuantity() {
		return executionQuantity;
	}

	public void setExecutionQuantity(Float executionQuantity) {
		this.executionQuantity = executionQuantity;
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

	public OrderBook getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + ", instrument=" + instrument
				+ ", orderQuantity=" + orderQuantity + ", orderprice=" + orderprice + ", orderStatus=" + orderStatus
				+ ", orderType=" + orderType + ", orderBook=" + orderBook + ", executionQuantity=" + executionQuantity
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

	
	

}
