package service.impl;

import entity.Order;
import entity.OrderItem;
import entity.OrderStatus;
import entity.Product;
import exception.*;
import repository.OrderRepository;
import service.InventoryService;
import service.OrderService;
import util.OptimisticRetryUtil;

import java.util.*;
import java.util.logging.Logger;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderServiceImpl(OrderRepository orderRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(int orderId) throws ResourceNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public void placeOrder(Order order) {
        List<OrderItem> processedItems = new ArrayList<>();
        try {
            double totalPrice = 0;
            for (OrderItem item : order.getOrderItems()) {
                Product product = inventoryService.getProductById(item.getProductId());
                if (!product.getPrice().equals(item.getPrice())) {
                    throw new PriceMismatchException("Price mismatch for product ID: " + item.getProductId());
                }
                inventoryService.updateStock(item.getProductId(), -item.getQuantity());
                processedItems.add(item.clone());
                totalPrice += item.getPrice() * item.getQuantity();
            }
            order.setTotalAmount(totalPrice);
            orderRepository.save(order);
        } catch (RuntimeException e) {
            rollBackStockUpdates(processedItems);
            throw new OrderPlacementException("Error placing order: " + e.getMessage());
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        Set<OrderItem> processedItems = new HashSet<>();
        int maxRetries = OptimisticRetryUtil.MAX_RETRY_ATTEMPTS;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Order order = getOrderById(orderId);
                if (order.getStatus() == OrderStatus.CANCELLED) {
                    throw new IllegalArgumentException("Order is already cancelled.");
                }
                for (OrderItem item : order.getOrderItems()) {
                    if (!processedItems.contains(item)) {
                        inventoryService.updateStock(item.getProductId(), item.getQuantity());
                        OrderItem processedItem = item.clone();
                        processedItem.setQuantity(-item.getQuantity());
                        processedItems.add(item);
                    }
                }
                order.setStatus(OrderStatus.CANCELLED);
                order.setUpdateDate(new Date());
                orderRepository.save(order);
                return;
            } catch (ConcurrentModificationException | OptimisticLockException ex) {
                if (attempt == maxRetries) {
                    throw new ConcurrentModificationException("Failed to cancel order after " + maxRetries + " attempts due to concurrent modifications.");
                }

                // Exponential backoff before retrying
                OptimisticRetryUtil.expBackOff(attempt);
            } catch (RuntimeException e) {
                rollBackStockUpdates(new ArrayList<>(processedItems));
                throw new OrderUpdateException("Error cancelling order: " + e.getMessage());
            }
        }
    }

    private void rollBackStockUpdates(List<OrderItem> items) {
        for (OrderItem item : items) {
            try {
                inventoryService.updateStock(item.getProductId(), item.getQuantity());
            } catch (RuntimeException e) {
                logger.severe("Failed to roll back stock for product ID: " + item.getProductId());
                logger.severe("Quantity to roll back: " + item.getQuantity());
            }
        }
    }
}
