import entity.Order;
import entity.OrderItem;
import entity.Product;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.impl.OrderRepositoryImpl;
import repository.impl.ProductRepositoryImpl;
import service.InventoryService;
import service.OrderService;
import service.impl.InventoryServiceImpl;
import service.impl.OrderServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("E-commerce System Initialized");
        System.out.println("-----------------------------------");

        ProductRepository productRepository = new ProductRepositoryImpl();
        InventoryService inventoryService = new InventoryServiceImpl(productRepository);

        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderService orderService = new OrderServiceImpl(productRepository, orderRepository, inventoryService);

        Product product = new Product("Black T-shirt", 500, 5);
        try {
            inventoryService.addProduct(product);
            System.out.println("Product added: " + product.getName());
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
        System.out.println("-----------------------------------");

        try {
            OrderItem orderItem = new OrderItem(1, 2);
            Order newOrder = new Order(List.of(orderItem));
            orderService.placeOrder(newOrder);
            Product orderedProduct = productRepository.getProductById(1).orElseThrow();
            System.out.println("Order placed: " + orderedProduct.getName());
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
        }
        System.out.println("-----------------------------------");

        System.out.println("Inventory:");
        for (Product inventoryProduct : productRepository.getAllProducts()) {
            System.out.println(inventoryProduct);
        }
        System.out.println("-----------------------------------");

        System.out.println("Order History:");
        for (Order pastOrder : orderRepository.getAllOrders()) {
            System.out.println(pastOrder);
        }
        System.out.println("-----------------------------------");

        try {
            orderService.cancelOrder(1);
            System.out.println("Order cancelled: 1");
        } catch (Exception e) {
            System.err.println("Error cancelling order: " + e.getMessage());
        }
        System.out.println("-----------------------------------");

        System.out.println("Inventory:");
        for (Product inventoryProduct : productRepository.getAllProducts()) {
            System.out.println(inventoryProduct);
        }
        System.out.println("-----------------------------------");

        System.out.println("Order History:");
        for (Order pastOrder : orderRepository.getAllOrders()) {
            System.out.println(pastOrder);
        }
    }
}