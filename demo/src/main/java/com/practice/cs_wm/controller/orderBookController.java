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
import com.practice.cs_wm.serviceImpl.ServiceFactory;

@Controller
@Path("/orderBooks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class orderBookController {

	
	@Autowired
	ServiceFactory serviceFactory;

	@GET
	public List<OrderBook> getOrderBooks() {
		return ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE))
				.getAllOrderBooks();
	}

	@POST
	public OrderBook addOrderBook(OrderBook orderBook, @Context UriInfo uriInfo) {
		return ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).addOrderBook(orderBook);
	}

	@PUT
	@Path("/{orderBookId}")
	public OrderBook updateOrderBook(@PathParam("orderBookId") long orderBookid, OrderBook orderBook) {
		return ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).updateOrderBook(orderBook);
	}

	@DELETE
	@Path("/{orderBookId}")
	public void deleteOrderBook(@PathParam("orderBookId") long orderBookId) {
		((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).removeOrderBook(orderBookId);
	}

	@GET
	@Path("/{orderBookId}")
	public OrderBook getOrderBook(@PathParam("orderBookId") long orderBookid, @Context UriInfo uriInfo) {
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(orderBookid);
		return orderBook;

	}

	@RequestMapping(value = { "admin/myorderbook" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/myorderbook");
		modelAndView.addObject("userName", "Welcome " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());

		List<OrderBook> orderBooks = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getAllOrderBooks();
		modelAndView.addObject("orderBooks", orderBooks);
		modelAndView.addObject("adminMessage", "Orders Inventory");
		return modelAndView;
	}

	@RequestMapping(value = "/orderBookDelete/{orderBookId}", method = RequestMethod.GET)
	public String delete(@PathVariable long orderBookId) {
		((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).removeOrderBook(orderBookId);
		return "redirect:/admin/myorderbook";
	}

	@ExceptionHandler({ DataNotFoundException.class })
	@RequestMapping(value = "/saveOrderBook", method = RequestMethod.POST)
	public String saveOrderBook(OrderBook orderBook, BindingResult bindingResult) {
		orderBook.setCreatedBy(((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		orderBook.setCreatedOn(new Date());
		((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).addOrderBook(orderBook);
		return "redirect:/admin/myorderbook";
	}

	@RequestMapping(value = "/orderBookEdit/{orderBookId}", method = RequestMethod.GET)
	public ModelAndView getOrderBook(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(orderBookId);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("instrumentMap", ((InstrumentService) serviceFactory.getService(ApplicationConstants.INSTRUMENT_SERVICE)).getAllInstrumentMap());
		modelAndView.addObject("bookStatusMap", ((RefOrderBookStatusService) serviceFactory.getService(ApplicationConstants.REF_ORDER_BOOK_STATUS_SERVICE)).getBookStatusMap());
		modelAndView.addObject("adminMessage", "Modify  - Order Book");
		modelAndView.addObject("userName", "Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/editorderbook");
		return modelAndView;
	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/acceptOrders", method = RequestMethod.GET)
	public ModelAndView getOrderBookAcceptOrders(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(orderBookId);
		List<Order> orphanOrders = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName", "Welcome :" + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;
	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/close", method = RequestMethod.GET)
	public ModelAndView closeOrderBook(@PathParam("orderBookId") long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).openCloseOrderBook(orderBookId, ApplicationConstants.ORDER_BOOK_STATUS_CLOSE);
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(orderBookId);
		List<Order> orphanOrders = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName", "Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;

	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/open")
	public OrderBook openOrderBook(@PathParam("orderBookId") long orderBookId, OrderBook orderBook) {
		return ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).openCloseOrderBook(orderBookId, ApplicationConstants.ORDER_BOOK_STATUS_OPEN);
	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/acceptOrder/{orderId}", method = RequestMethod.GET)
	public ModelAndView acceptOrderForBook(@PathVariable long orderBookId, @PathVariable long orderId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).acceptOrderForOrderBook(orderBookId, orderId);
		List<Order> orphanOrders = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrphanOrders(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("orphanOrders", orphanOrders);
		modelAndView.addObject("adminMessage", "Order Book - Accept Orders");
		modelAndView.addObject("userName", "Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/accptOrders");
		return modelAndView;
	}

	@RequestMapping(value = "/createOrderBook", method = RequestMethod.GET)
	public ModelAndView getCreateOrderBook() {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).createDefaultOrderBook();
		// orderBook=((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).addOrderBook(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("instrumentMap", ((InstrumentService) serviceFactory.getService(ApplicationConstants.INSTRUMENT_SERVICE)).getAllInstrumentMap());
		modelAndView.addObject("bookStatusMap", ((RefOrderBookStatusService) serviceFactory.getService(ApplicationConstants.REF_ORDER_BOOK_STATUS_SERVICE)).getBookStatusMap());
		modelAndView.addObject("adminMessage", "Create - Order Book");
		modelAndView.addObject("userName", "Welcome : " + ((UserService) serviceFactory.getService(ApplicationConstants.USER_SERVICE)).getUserName());
		modelAndView.setViewName("admin/editorderbook");
		return modelAndView;
	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/addExecutionToOrderBook", method = RequestMethod.GET)
	public ModelAndView addExecution(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBook orderBook = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBook(orderBookId);
		Execution execution = ((ExecutionService) serviceFactory.getService(ApplicationConstants.EXECUTION_SERVICE)).getTempExecutionForOrder(orderBook);
		modelAndView.addObject("orderBook", orderBook);
		modelAndView.addObject("execution", execution);
		modelAndView.addObject("adminMessage", "Order Book - Add Execution");
		modelAndView.setViewName("admin/addExecution");
		return modelAndView;
	}

	@RequestMapping(value = "/addExecution", method = RequestMethod.POST)
	public String addExecution(Execution execution) {
		((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).addExecution(execution);
		return "redirect:/orderBookEdit/" + execution.getOrderBook().getOrderBookId();
	}

	@RequestMapping(value = "/orderBookEdit/{orderBookId}/orderEdit/{OrderId}", method = RequestMethod.GET)
	public String EditOrderBookOrder(@PathVariable long orderBookId, @PathVariable long OrderId) {
		{
			return "redirect:/orderEdit/" + OrderId;
		}
	}

	@RequestMapping(value = "/orderBooks/{orderBookId}/getBookStats", method = RequestMethod.GET)
	public ModelAndView getOrderBookStats(@PathVariable long orderBookId) {
		ModelAndView modelAndView = new ModelAndView();
		OrderBookStatsVo orderBookStatsVo = ((OrderBookService) serviceFactory.getService(ApplicationConstants.ORDER_BOOK_SERVICE)).getOrderBookStats(orderBookId);
		modelAndView.addObject("orderBookStatsVo", orderBookStatsVo);
		modelAndView.addObject("orderBook", orderBookStatsVo.getOrderBook());
		modelAndView.addObject("adminMessage", "Order Book - Statistics");
		modelAndView.setViewName("admin/orderBookStats");
		return modelAndView;
	}

}
