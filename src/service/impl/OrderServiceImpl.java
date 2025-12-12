package service.impl;

import entity.Order;
import entity.OrderItem;
import entity.OrderStatus;
import entity.Product;
import repository.impl.OrderRepositoryImpl;
import repository.impl.ProductRepositoryImpl;
import service.InventoryService;
import service.OrderService;

public class OrderServiceImpl implements OrderService {

    private final ProductRepositoryImpl productRepositoryImpl;
    private final OrderRepositoryImpl orderRepositoryImpl;
    private final InventoryService inventoryService;

    public OrderServiceImpl(ProductRepositoryImpl productRepositoryImpl, OrderRepositoryImpl orderRepositoryImpl, InventoryService inventoryService) {
        this.productRepositoryImpl = productRepositoryImpl;
        this.orderRepositoryImpl = orderRepositoryImpl;
        this.inventoryService = inventoryService;
    }

    @Override
    public void placeOrder(Order order) throws Exception {
        double totalPrice = 0;
        for (OrderItem item : order.getOrderItems()) {
            Product orderedProduct = productRepositoryImpl.getProductById(item.getProductId())
                    .orElseThrow(() -> new Exception("Product not found: " + item.getProductId()));
            inventoryService.updateStock(orderedProduct.getProductId(), -item.getQuantity());
            item.setPrice(orderedProduct.getListPrice());
            totalPrice += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalPrice);
        orderRepositoryImpl.saveOrder(order);
    }

    @Override
    public void cancelOrder(int orderId) throws Exception {
        try {
            Order order = orderRepositoryImpl.getOrderById(orderId)
                    .orElseThrow(() -> new Exception("Order not found"));
            if (order.getStatus() == OrderStatus.CANCELLED) {
                throw new Exception("Order is already cancelled");
            }
            order.setStatus(OrderStatus.CANCELLED);
            orderRepositoryImpl.saveOrder(order);
            for (OrderItem item : order.getOrderItems()) {
                inventoryService.updateStock(item.getProductId(), item.getQuantity());
            }
        } catch (Exception e) {
            throw new Exception("Error cancelling order: " + e.getMessage());
        }
    }
}
