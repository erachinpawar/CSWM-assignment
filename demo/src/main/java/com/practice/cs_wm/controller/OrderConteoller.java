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

import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.modelVos.OrderStatsVo;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderService;
import com.practice.cs_wm.service.OrderTypeService;
import com.practice.cs_wm.service.UserService;

@Path("/Orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class OrderConteoller {
	
	@Autowired
	OrderService orderService;
	@Autowired
	private InstrumentService instrumentService;
	@Autowired
	OrderTypeService orderTypeService;
	
	@Autowired
	private UserService userService;
	
	
	@GET
	public List<Order> getOrders() {
		return orderService.getAllOrders();
	}
	
	@POST
	public Order addOrder(Order order, @Context UriInfo uriInfo) {
		Order newOrder = orderService.addOrder(order);
		return newOrder;
	}
	
	@PUT
	@Path("/{orderId}")
	public Order updateOrder(@PathParam("orderId") long orderid, Order order) {
		order.setOrderId(orderid);
		return orderService.updateOrder(order);
	}
	
	@DELETE
	@Path("/{orderId}")
	public void deleteOrder(@PathParam("orderId") long orderId) {
		orderService.removeOrder(orderId);
	}
	
	@GET
	@Path("/{orderId}")
	public Order getOrder(@PathParam("orderId") long orderid, @Context UriInfo uriInfo) {
		Order order = orderService.getOrder(orderid);
		return order;
		
	}
	
	
	@RequestMapping(value="admin/myOrders",method = RequestMethod.GET)    
    public ModelAndView getAllMyOrders(){ 
		List<Order> allOrders = getOrders();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("allOrders", allOrders);
		modelAndView.addObject("adminMessage", "Page - Order Book");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/myOrders");
        return modelAndView;    
    }  
	
	
	@RequestMapping(value="/orderDelete/{orderId}",method = RequestMethod.GET)    
    public String delete(@PathVariable long orderId){    
		Order order = orderService.getOrder(orderId);
		orderService.removeOrder(orderId);    
        return "redirect:/orderBookEdit/" + order.getOrderBook().getOrderBookId();    
    }  
	
	@RequestMapping(value="/orderEdit/{orderId}",method = RequestMethod.GET)    
    public ModelAndView getOrderBook(@PathVariable long orderId){    
		ModelAndView modelAndView = new ModelAndView();
		Order order = orderService.getOrder(orderId); 
		modelAndView.addObject("order", order);
		modelAndView.addObject("instrumentMap", instrumentService.getAllInstrumentMap());
		modelAndView.addObject("orderTypeMap", orderTypeService.getAllModelTypes());
		modelAndView.addObject("adminMessage", "Page - Order Book");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/editorder");
        return modelAndView;    
    } 
	
	@RequestMapping(value="/createOrder",method = RequestMethod.GET)    
    public ModelAndView getCreateOrder(){    
		ModelAndView modelAndView = new ModelAndView();
		Order order = orderService.createDefaultOrder(); 
		modelAndView.addObject("order", order);
		modelAndView.addObject("instrumentMap", instrumentService.getAllInstrumentMap());
		modelAndView.addObject("orderTypeMap", orderTypeService.getAllModelTypes());
		modelAndView.addObject("adminMessage", "Page - Order Book");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/editorder");
        return modelAndView;    
    } 
	
	@RequestMapping(value="/saveOrder",method = RequestMethod.POST)    
    public String saveOrderBook(@Valid Order order, BindingResult bindingResult){
		 order.setCreatedBy(userService.getUserName());
		 order.setCreatedOn(new Date());
		 orderService.addOrder(order);
		 return "redirect:admin/myOrders";
	}
	@RequestMapping(value = "/orders/{orderId}/getOrderStats", method = RequestMethod.GET)
	public ModelAndView getOrderBookStats(@PathVariable long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderStatsVo orderStatsVo=orderService.getOrderBookStats(orderId);
		Order order = orderService.getOrder(orderId);
		modelAndView.addObject("orderStatsVo", orderStatsVo);
		modelAndView.addObject("adminMessage", "Order Statistics");
		modelAndView.addObject("order", order);
		modelAndView.setViewName("admin/orderStats");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		return modelAndView;
	}

}
