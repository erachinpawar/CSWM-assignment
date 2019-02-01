package com.practice.cs_wm.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.modelVos.OrderStatsVo;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderService;
import com.practice.cs_wm.service.OrderTypeService;
import com.practice.cs_wm.service.UserService;
import com.practice.cs_wm.serviceImpl.ServiceFactory;

@Path("/Orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class OrderConteoller {
	
	@Autowired
	ServiceFactory serviceFactory;
	
	
	@GET
	public List<Order> getOrders() {
		return ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).getAllOrders();
	}
	
	@POST
	public Order addOrder(Order order, @Context UriInfo uriInfo) {
		Order newOrder = ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).addOrder(order);
		return newOrder;
	}
	
	@PUT
	@Path("/{orderId}")
	public Order updateOrder(@PathParam("orderId") long orderid, Order order) {
		order.setOrderId(orderid);
		return ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).updateOrder(order);
	}
	
	@DELETE
	@Path("/{orderId}")
	public void deleteOrder(@PathParam("orderId") long orderId) {
		((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).removeOrder(orderId);
	}
	
	@GET
	@Path("/{orderId}")
	public Order getOrder(@PathParam("orderId") long orderid, @Context UriInfo uriInfo) {
		Order order = ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).getOrder(orderid);
		return order;
		
	}
	
	
	@RequestMapping(value="admin/myOrders",method = RequestMethod.GET)    
    public ModelAndView getAllMyOrders(){ 
		List<Order> allOrders = getOrders();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("allOrders", allOrders);
		modelAndView.addObject("adminMessage", "Orders Inventory");
		modelAndView.addObject("userName",
				"Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/myOrders");
        return modelAndView;    
    }  
	
	
	@RequestMapping(value="/orderDelete/{orderId}",method = RequestMethod.GET)    
    public ModelAndView delete(@PathVariable long orderId){    
		((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).removeOrder(orderId);    
		List<Order> allOrders = getOrders();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("allOrders", allOrders);
		modelAndView.addObject("adminMessage", "Orders Inventory");
		modelAndView.addObject("userName",
				"Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/myOrders");
        return modelAndView;      
    }  
	
	@RequestMapping(value="/orderEdit/{orderId}",method = RequestMethod.GET)    
    public ModelAndView getOrderBook(@PathVariable long orderId){    
		ModelAndView modelAndView = new ModelAndView();
		Order order = ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).getOrder(orderId); 
		modelAndView.addObject("order", order);
		modelAndView.addObject("instrumentMap", ((InstrumentService) serviceFactory.getService(ApplicationConstants.INSTRUMENT_SERVICE)).getAllInstrumentMap());
		modelAndView.addObject("orderTypeMap", ((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getAllModelTypes());
		modelAndView.addObject("adminMessage", "Modify - Order");
		modelAndView.addObject("userName",
				"Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/editorder");
        return modelAndView;    
    } 
	
	@RequestMapping(value="/createOrder",method = RequestMethod.GET)    
    public ModelAndView getCreateOrder(){    
		ModelAndView modelAndView = new ModelAndView();
		Order order = ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).createDefaultOrder(); 
		modelAndView.addObject("order", order);
		modelAndView.addObject("instrumentMap", ((InstrumentService) serviceFactory.getService(ApplicationConstants.INSTRUMENT_SERVICE)).getAllInstrumentMap());
		modelAndView.addObject("orderTypeMap", ((OrderTypeService) serviceFactory.getService(ApplicationConstants. REF_ORDER_TYPE_SERVICE)).getAllModelTypes());
		modelAndView.addObject("adminMessage", "Create - Order ");
		modelAndView.addObject("userName",
				"Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/editorder");
        return modelAndView;    
    } 
	
	@RequestMapping(value="/saveOrder",method = RequestMethod.POST)    
    public String saveOrderBook(@Valid Order order, BindingResult bindingResult){
		 order.setCreatedBy(((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		 order.setCreatedOn(new Date());
		 ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).addOrder(order);
		 return "redirect:admin/myOrders";
	}
	@RequestMapping(value = "/orders/{orderId}/getOrderStats", method = RequestMethod.GET)
	public ModelAndView getOrderBookStats(@PathVariable long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderStatsVo orderStatsVo=((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).getOrderBookStats(orderId);
		Order order = ((OrderService) serviceFactory.getService(ApplicationConstants.ORDER_SERVICE)).getOrder(orderId);
		modelAndView.addObject("orderStatsVo", orderStatsVo);
		modelAndView.addObject("adminMessage", "Order Statistics");
		modelAndView.addObject("order", order);
		modelAndView.setViewName("admin/orderStats");
		modelAndView.addObject("userName",
				"Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		return modelAndView;
	}

}
