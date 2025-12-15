package repository.impl;

import entity.Order;
import repository.OrderRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepositoryImpl implements OrderRepository {
    private int nextId;
    Map<Integer, Order> orderData;

    public OrderRepositoryImpl() {
        nextId = 1;
        orderData = new ConcurrentHashMap<>();
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
            order.setOrderId(nextId++);
        }
        orderData.put(order.getOrderId(), order);
    }
}
