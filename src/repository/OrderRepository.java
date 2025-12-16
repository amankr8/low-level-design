package repository;

import entity.Order;
import entity.OrderItem;
import exception.OptimisticLockException;
import exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRepository implements BaseRepository<Integer, Order> {
    private final AtomicInteger nextId;
    private final Map<Integer, Order> orderData;

    public OrderRepository() {
        nextId = new AtomicInteger(1);
        orderData = new ConcurrentHashMap<>();
    }

    @Override
    public List<Order> findAll() {
        return orderData.values().stream().toList();
    }

    @Override
    public Optional<Order> findById(Integer orderId) {
        return Optional.ofNullable(orderData.get(orderId));
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == 0) {
            return insert(order);
        } else {
            return update(order);
        }
    }

    @Override
    public void deleteById(Integer productId) {
        orderData.remove(productId);
    }

    private Order insert(Order order) {
        int id = nextId.getAndIncrement();
        Order newOrder = order.copy();
        newOrder.setOrderId(id);
        newOrder.setVersion(0);
        orderData.put(id, newOrder);
        return newOrder.copy();
    }

    private Order update(Order order) {
        return orderData.compute(order.getOrderId(), (id, existingOrder) -> {
            if (existingOrder == null) {
                throw new ResourceNotFoundException("Order with ID: " + order.getOrderId() + " not found.");
            }

            if (existingOrder.getVersion() != order.getVersion()) {
                throw new OptimisticLockException("Order with ID: " + order.getOrderId() + " has been modified by another transaction.");
            }

            Order updatedOrder = order.copy();
            updatedOrder.setVersion(existingOrder.getVersion() + 1);
            return updatedOrder;
        }).copy();
    }
}
