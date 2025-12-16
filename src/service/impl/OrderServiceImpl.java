package service.impl;

import entity.Order;
import entity.OrderItem;
import entity.OrderStatus;
import entity.Product;
import exception.InsufficientStockException;
import exception.OrderPlacementException;
import exception.PriceMismatchException;
import exception.ResourceNotFoundException;
import repository.OrderRepository;
import repository.ProductRepository;
import service.InventoryService;
import service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, InventoryService inventoryService) {
        this.productRepository = productRepository;
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
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                if (!product.getPrice().equals(item.getPrice())) {
                    throw new PriceMismatchException("Price mismatch for product ID: " + item.getProductId());
                }
                inventoryService.updateStock(item.getProductId(), -item.getQuantity());
                processedItems.add(item);
                totalPrice += item.getPrice() * item.getQuantity();
            }
            order.setTotalAmount(totalPrice);
            orderRepository.save(order);
        } catch (RuntimeException e) {
            rollBackStockUpdates(processedItems);
            throw new OrderPlacementException("Error placing order: " + e.getMessage());
        }
    }

    private void rollBackStockUpdates(List<OrderItem> items) {
        for (OrderItem item : items) {
            try {
                inventoryService.updateStock(item.getProductId(), item.getQuantity());
            } catch (Exception e) {
                logger.severe("Failed to roll back stock for product ID: " + item.getProductId());
                logger.severe("Quantity to roll back: " + item.getQuantity());
            }
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Order is already cancelled");
        }
        for (OrderItem item : order.getOrderItems()) {
            inventoryService.updateStock(item.getProductId(), item.getQuantity());
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdateDate(new Date());
        orderRepository.save(order);
    }
}
