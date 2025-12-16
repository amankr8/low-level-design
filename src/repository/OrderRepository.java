package repository;

import entity.Order;

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
        if (!orderData.containsKey(order.getOrderId())) {
            int id = nextId.getAndIncrement();
            order.setOrderId(id);
        }
        orderData.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
