package com.practice.cs_wm.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.practice.cs_wm.ApplicationConstants;
import com.practice.cs_wm.exceptions.DataNotFoundException;
import com.practice.cs_wm.model.Execution;
import com.practice.cs_wm.model.Order;
import com.practice.cs_wm.model.OrderBook;
import com.practice.cs_wm.modelVos.OrderBookStatsVo;
import com.practice.cs_wm.service.ExecutionService;
import com.practice.cs_wm.service.InstrumentService;
import com.practice.cs_wm.service.OrderBookService;
import com.practice.cs_wm.service.RefOrderBookStatusService;
import com.practice.cs_wm.service.UserService;

@Controller
@Path("/orderBooks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class orderBookController {
//	@Autowired
//	private UserService userService;

	@Autowired
	private OrderBookService orderBookService;

	@Autowired
	private RefOrderBookStatusService bookStatusService ;
	
	@Autowired
	private InstrumentService instrumentService;
	
	@Autowired
	private ExecutionService executionService;

	@Autowired
	private UserService userService;
	

	@GET
	public List<OrderBook> getOrderBooks() {
		return orderBookService.getAllOrderBooks();
	}

	@POST
	public OrderBook addOrderBook(OrderBook orderBook, @Context UriInfo uriInfo) {
		return orderBookService.addOrderBook(orderBook);
	}

	@PUT
	@Path("/{orderBookId}")
	public OrderBook updateOrderBook(@PathParam("orderBookId") long orderBookid, OrderBook orderBook) {
		return orderBookService.updateOrderBook(orderBook);
	}

	@DELETE
	@Path("/{orderBookId}")
	public void deleteOrderBook(@PathParam("orderBookId") long orderBookId) {
		orderBookService.removeOrderBook(orderBookId);
	}

	@GET
	@Path("/{orderBookId}")
	public OrderBook getOrderBook(@PathParam("orderBookId") long orderBookid, @Context UriInfo uriInfo) {
		OrderBook orderBook = orderBookService.getOrderBook(orderBookid);
		return orderBook;

	}

	@RequestMapping(value = { "admin/myorderbook" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/myorderbook");
		modelAndView.addObject("userName",
				"Welcome " +userService.getUserName());

		List<OrderBook> orderBooks = orderBookService.getAllOrderBooks();
		modelAndView.addObject("orderBooks", orderBooks);
		modelAndView.addObject("adminMessage", "Orders Inventory");
		return modelAndView;
	}

	@RequestMapping(value = "/orderBookDelete/{orderBookId}", method = RequestMethod.GET)
	public String delete(@PathVariable long orderBookId) {
		orderBookService.removeOrderBook(orderBookId);
		return "redirect:/admin/myorderbook";
	}

	@ExceptionHandler({DataNotFoundException.class})
	@RequestMapping(value = "/saveOrderBook", method = RequestMethod.POST)
	public String saveOrderBook(OrderBook orderBook, BindingResult bindingResult) {
		orderBook.setCreatedBy(userService.getUserName());
		orderBook.setCreatedOn(new Date());
		orderBookService.addOrderBook(orderBook);
		return "redirect:/admin/myorderbook";
	}

	@RequestMapping(value = "/orderBookEdit/{orderBookId}", method = RequestMethod.GET)
	public ModelAndView getOrderBook(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = orderBookService.getOrderBook(orderBookId);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("instrumentMap", instrumentService.getAllInstrumentMap());
		modelAndView.addObject("bookStatusMap", bookStatusService.getBookStatusMap());
		modelAndView.addObject("adminMessage", "Modify  - Order Book");
		modelAndView.addObject("userName",
				"Welcome : " +userService.getUserName());
		modelAndView.setViewName("admin/editorderbook");
		return modelAndView;
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/acceptOrders", method = RequestMethod.GET)
	public ModelAndView getOrderBookAcceptOrders(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = orderBookService.getOrderBook(orderBookId);
		List<Order> orphanOrders = orderBookService.getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName",
				"Welcome :" + userService.getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/close", method = RequestMethod.GET)
	public ModelAndView closeOrderBook(@PathParam("orderBookId") long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		 orderBookService.openCloseOrderBook(orderBookId,ApplicationConstants.ORDER_BOOK_STATUS_CLOSE);
		OrderBook orderBook = orderBookService.getOrderBook(orderBookId);
		List<Order> orphanOrders = orderBookService.getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/open")
	public OrderBook openOrderBook(@PathParam("orderBookId") long orderBookId, OrderBook orderBook) {
		return orderBookService.openCloseOrderBook(orderBookId,ApplicationConstants.ORDER_BOOK_STATUS_OPEN);
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/acceptOrder/{orderId}", method = RequestMethod.GET)
	public ModelAndView acceptOrderForBook(@PathVariable long orderBookId,@PathVariable long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = orderBookService.acceptOrderForOrderBook(orderBookId,orderId);
		List<Order> orphanOrders = orderBookService.getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;
	}
	
	@RequestMapping(value = "/createOrderBook", method = RequestMethod.GET)
	public ModelAndView getCreateOrderBook() {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = orderBookService.createDefaultOrderBook();
		//orderBook=orderBookService.addOrderBook(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("instrumentMap", instrumentService.getAllInstrumentMap());
		modelAndView.addObject("bookStatusMap", bookStatusService.getBookStatusMap());
		modelAndView.addObject("adminMessage", "Create - Order Book");
		modelAndView.addObject("userName",
				"Welcome : " + userService.getUserName());
		modelAndView.setViewName("admin/editorderbook");
		return modelAndView;
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/addExecutionToOrderBook", method = RequestMethod.GET)
	public ModelAndView addExecution(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = orderBookService.getOrderBook(orderBookId);
		Execution execution = executionService.getTempExecutionForOrder(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("execution", execution);
		modelAndView.addObject("adminMessage", "Order Book - Add Execution");
		modelAndView.setViewName("admin/addExecution");
		return modelAndView;
	}

	@RequestMapping(value = "/addExecution", method = RequestMethod.POST)
	public String addExecution(Execution execution) {
		orderBookService.addExecution(execution);
		return "redirect:/orderBookEdit/"+execution.getOrderBook().getOrderBookId();
	}
	

	@RequestMapping(value = "/orderBookEdit/{orderBookId}/orderEdit/{OrderId}", method = RequestMethod.GET)
	public String EditOrderBookOrder(@PathVariable long orderBookId, @PathVariable long OrderId) {
		{
			return "redirect:/orderEdit/" +OrderId;
		}
	}
	
	@RequestMapping(value = "/orderBooks/{orderBookId}/getBookStats", method = RequestMethod.GET)
	public ModelAndView getOrderBookStats(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBookStatsVo orderBookStatsVo=orderBookService.getOrderBookStats(orderBookId);
		modelAndView.addObject("orderBookStatsVo", orderBookStatsVo);
		modelAndView.addObject("orderBook", orderBookStatsVo.getOrderBook());
		modelAndView.addObject("adminMessage", "Order Book - Statistics");
		modelAndView.setViewName("admin/orderBookStats");
		return modelAndView;
	}
	
}
