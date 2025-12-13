package service.impl;

import entity.Order;
import entity.OrderItem;
import entity.OrderStatus;
import entity.Product;
import repository.OrderRepository;
import repository.ProductRepository;
import service.InventoryService;
import service.OrderService;

import java.util.Date;

public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public void placeOrder(Order order) throws Exception {
        double totalPrice = 0;
        for (OrderItem item : order.getOrderItems()) {
            Product orderedProduct = productRepository.getProductById(item.getProductId())
                    .orElseThrow(() -> new Exception("Product not found: " + item.getProductId()));
            inventoryService.updateStock(orderedProduct.getProductId(), -item.getQuantity());
            item.setPrice(orderedProduct.getListPrice());
            totalPrice += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalPrice);
        orderRepository.saveOrder(order);
    }

    @Override
    public void cancelOrder(int orderId) throws Exception {
        try {
            Order order = orderRepository.getOrderById(orderId)
                    .orElseThrow(() -> new Exception("Order not found"));
            if (order.getStatus() == OrderStatus.CANCELLED) {
                throw new Exception("Order is already cancelled");
            }
            order.setStatus(OrderStatus.CANCELLED);
            order.setUpdateDate(new Date());
            orderRepository.saveOrder(order);
            for (OrderItem item : order.getOrderItems()) {
                inventoryService.updateStock(item.getProductId(), item.getQuantity());
            }
        } catch (Exception e) {
            throw new Exception("Error cancelling order: " + e.getMessage());
        }
    }
}
