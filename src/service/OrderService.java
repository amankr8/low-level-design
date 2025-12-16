package service;

import entity.Order;
import exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(int orderId) throws ResourceNotFoundException;

    void placeOrder(Order order) throws Exception;

    void cancelOrder(int orderId) throws Exception;
}
