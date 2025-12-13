package repository.impl;

import entity.Order;
import repository.OrderRepository;
import util.RepositoryHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    Map<Integer, Order> orderData;

    public OrderRepositoryImpl() {
        orderData = new HashMap<>();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderData.values().stream().toList();
    }

    @Override
    public Optional<Order> getOrderById(int orderId) {
        return Optional.ofNullable(orderData.get(orderId));
    }

    @Override
    public void saveOrder(Order order) {
        if (order.getOrderId() == 0) {
            order.setOrderId(RepositoryHelper.getNextIdForMap(orderData));
        }
        orderData.put(order.getOrderId(), order);
    }
}
