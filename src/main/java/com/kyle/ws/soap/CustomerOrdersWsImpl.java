package com.kyle.ws.soap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.feature.Features;

import com.bharath.ws.trainings.CreateOrdersRequest;
import com.bharath.ws.trainings.CreateOrdersResponse;
import com.bharath.ws.trainings.CustomerOrdersPortType;
import com.bharath.ws.trainings.GetOrdersRequest;
import com.bharath.ws.trainings.GetOrdersResponse;
import com.bharath.ws.trainings.Order;
import com.bharath.ws.trainings.Product;

@Features(features="org.apache.cxf.feature.LoggingFeature")
public class CustomerOrdersWsImpl implements CustomerOrdersPortType {
	
	
	Map<BigInteger, List<Order>> customerOrders = new HashMap<>();
	int currentId;
	
	public CustomerOrdersWsImpl() {
		init();
	}
	
	public void init() {
		//Create list of orders
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		order.setId(BigInteger.valueOf(1));
		
		//Instantiate a Product
		Product product = new Product();
		product.setId("1");
		product.setDescription("iPhone7");
		product.setQuantity(BigInteger.valueOf(3));
		order.getProduct().add(product);
		
		//Add Order to the list of order
		orders.add(order);
		
		//Add list of orders and CustomerID to the HashMap
		customerOrders.put(BigInteger.valueOf(++currentId), orders);
	}

	@Override
	public GetOrdersResponse getOrders(GetOrdersRequest request) {
		BigInteger customerId = request.getCustomerId();
		List <Order> orders = customerOrders.get(customerId);
		GetOrdersResponse response = new GetOrdersResponse();
		response.getOrder().addAll(orders);
		
		return response;
	}

	@Override
	public CreateOrdersResponse createOrders(CreateOrdersRequest request) {
		BigInteger customerId = request.getCustomerId();
		Order order = request.getOrder();
		
		List<Order> orders = customerOrders.get(customerId);
		orders.add(order);
		
		CreateOrdersResponse response = new CreateOrdersResponse();
		response.setResult(true);
		
		return response;
	}

}
