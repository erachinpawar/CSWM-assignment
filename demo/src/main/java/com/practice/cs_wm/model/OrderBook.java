package com.practice.cs_wm.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "order_book_inv")
public class OrderBook {

	@Id
	@Column(name = "order_book_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_book_inv_seq")
	private long orderBookId;

	@Column(name = "order_book_name")
	private String orderBookName;
	
	@OneToOne
	@Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "instrument_id")
	private Instrument instrument;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_book_status_id", nullable = false)
	private OrderBookStatus orderBookStatus;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orderBook")
	@JsonManagedReference
	private Set<Execution> executions;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "orderBook")
	@JsonManagedReference
	private Set<Order> orders;
	
	@Column(name="is_book_executed")
	private String executionStatus;

	@Column(name = "created_by")
	private String createdBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_on")
	private Date createdOn;
	
	public long getOrderBookId() {
		return orderBookId;
	}
	public void setOrderBookId(long orderBookId) {
		this.orderBookId = orderBookId;
	}
	public String getOrderBookName() {
		return orderBookName;
	}
	public void setOrderBookName(String orderBookName) {
		this.orderBookName = orderBookName;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public OrderBookStatus getOrderBookStatus() {
		return orderBookStatus;
	}
	public void setOrderBookStatus(OrderBookStatus orderBookStatus) {
		this.orderBookStatus = orderBookStatus;
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
	
	
	public String getBookExecutionStatus() {
		return executionStatus;
	}
	public void setBookExecutionStatus(String bookExecutionStatus) {
		this.executionStatus = bookExecutionStatus;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	public Set<Execution> getExecutions() {
		return executions;
	}
	public void setExecutions(Set<Execution> executions) {
		this.executions = executions;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "OrderBook [orderBookId=" + orderBookId + ", orderBookName=" + orderBookName + ", instrument="
				+ instrument + ", orderBookStatus=" + orderBookStatus + ", executions=" + executions + ", orders="
				+ orders + ", executionStatus=" + executionStatus + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + "]";
	}

	
	
	
	
	
	

	

}
