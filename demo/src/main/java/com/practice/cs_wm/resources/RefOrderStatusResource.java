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

import com.practice.cs_wm.model.OrderStatus;
import com.practice.cs_wm.service.RefOrderStatusService;

@Path("/refOrderStatuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefOrderStatusResource {
	
	@Autowired
	RefOrderStatusService refOrderStatusService;
	
	@GET
	public List<OrderStatus> getRefOrderStatuses() {
		List<OrderStatus> refOrderStatusList = refOrderStatusService.getAllOrderStatuses();
		return refOrderStatusList;
	}
	
	
	@POST
	public OrderStatus addRefOrderStatus(OrderStatus refOrderStatus) {
		return refOrderStatusService.addOrderStatus(refOrderStatus);
	}
	@GET
	@Path("/{orderStatusId}")
	public OrderStatus getRefOrderStatus(@PathParam("orderStatusId") long orderStatusId) {
		return refOrderStatusService.getOrderStatus(orderStatusId);
	}
	
	@PUT
	@Path("/{orderStatusId}")
	public OrderStatus updateRefOrderStatus(@PathParam("orderStatusId") long orderStatusId, OrderStatus refOrderStatus) {
		refOrderStatus.setOrderStatusId(orderStatusId);
		return refOrderStatusService.updateOrderStatus(refOrderStatus);
	}


	@DELETE
	@Path("/{orderStatusId}")
	public void deleteRefOrderStatus(@PathParam("orderStatusId") long orderStatusId) {
		refOrderStatusService.removeOrderStatus(orderStatusId);
	}

}
