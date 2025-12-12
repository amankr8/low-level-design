package service;

import entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void placeOrder(Order order) throws Exception;

    void cancelOrder(int orderId) throws Exception;
}
