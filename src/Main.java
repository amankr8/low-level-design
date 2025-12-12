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
        System.out.println("E-commerce System Initialized");
        System.out.println("-----------------------------------");

        ProductRepository productRepository = new ProductRepositoryImpl();
        InventoryService inventoryService = new InventoryServiceImpl(productRepository);

        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderService orderService = new OrderServiceImpl(productRepository, orderRepository, inventoryService);

        Product product1 = new Product("Black T-shirt", 500, 5);
        Product product2 = new Product("Blue Cap", 400, 2);
        try {
            inventoryService.addProduct(product1);
            System.out.println("Product added: " + product1.getName());
            inventoryService.addProduct(product2);
            System.out.println("Product added: " + product2.getName());
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
        System.out.println("-----------------------------------");

        try {
            OrderItem orderItem1 = new OrderItem(1, 2);
            OrderItem orderItem2 = new OrderItem(2, 1);
            Order newOrder = new Order(List.of(orderItem1, orderItem2));
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