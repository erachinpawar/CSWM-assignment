package com.practice.cs_wm.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.practice.cs_wm.model.OrderBookStatus;
import com.practice.cs_wm.service.RefOrderBookStatusService;

@Path("/refOrderBookStatuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefOrderBookStatusResource {

	
	@Autowired
	RefOrderBookStatusService refOrderBookStatusService;
	
	@GET
	public List<OrderBookStatus> getRefOrderBookStatuses() {
		List<OrderBookStatus> refOrderBookStatusList = refOrderBookStatusService.getAllOrderBookStatuses();
		return refOrderBookStatusList;
	}
	
	@POST
	public OrderBookStatus addRefOrderBookStatus(OrderBookStatus refOrderBookStatus) {
		return refOrderBookStatusService.addOrderBookStatus(refOrderBookStatus);
	}
	@GET
	@Path("/{orderBookStatusId}")
	public OrderBookStatus getRefOrderBookStatus(@PathParam("orderBookStatusId") long orderBookStatusId) {
		return refOrderBookStatusService.getOrderBookStatus(orderBookStatusId);
	}
	
	@PUT
	@Path("/{orderBookStatusId}")
	public OrderBookStatus updateRefOrderBookStatus(@PathParam("orderBookStatusId") long orderBookStatusId, OrderBookStatus refOrderBookStatus) {
		refOrderBookStatus.setOrderBookStatusId(orderBookStatusId);
		return refOrderBookStatusService.updateOrderBookStatus(refOrderBookStatus);
	}


	@DELETE
	@Path("/{orderBookStatusId}")
	public void deleteRefOrderBookStatus(@PathParam("orderBookStatusId") long orderBookStatusId) {
		refOrderBookStatusService.removeOrderBookStatus(orderBookStatusId);
	}
}
