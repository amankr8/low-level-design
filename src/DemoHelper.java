import entity.Order;
import entity.OrderItem;
import entity.Product;
import entity.User;
import service.AuthService;
import service.InventoryService;
import service.OrderService;

import java.util.List;

public class DemoHelper {
    public static void runDemo(AuthService authService, InventoryService inventoryService, OrderService orderService) throws Exception {
        System.out.println("E-commerce System Initialized");
        System.out.println("-----------------------------------");

        Product product1 = new Product("Black T-shirt", 500.0, 5);
        Product product2 = new Product("Blue Cap", 400.0, 2);
        inventoryService.addProduct(product1);
        System.out.println("Product added: " + product1.getName());
        inventoryService.addProduct(product2);
        System.out.println("Product added: " + product2.getName());
        System.out.println("-----------------------------------");

        User customer = new User("amankr8", "pass1234");
        authService.signUp(customer.getUsername(), customer.getPassword());
        System.out.println("User created: " + customer.getUsername());
        System.out.println("-----------------------------------");

        User loggedInUser;
        loggedInUser = authService.signIn("amankr8", "pass1234");
        System.out.println("User: " + loggedInUser.getUsername() + " - signed in successfully");
        System.out.println("-----------------------------------");

        OrderItem orderItem1 = new OrderItem(1, 2, product1.getPrice());
        OrderItem orderItem2 = new OrderItem(2, 1, product2.getPrice());
        Order newOrder = new Order(loggedInUser.getUserId(), List.of(orderItem1, orderItem2));
        orderService.placeOrder(newOrder);
        Product orderedProduct = inventoryService.getProductById(1);
        System.out.println("Order placed: " + orderedProduct.getName() + ", By Customer: " + loggedInUser.getUsername());
        System.out.println("-----------------------------------");

        System.out.println("Inventory:");
        for (Product inventoryProduct : inventoryService.getAllProducts()) {
            System.out.println(inventoryProduct);
        }
        System.out.println("-----------------------------------");

        System.out.println("Order History:");
        for (Order pastOrder : orderService.getAllOrders()) {
            System.out.println(pastOrder);
        }
        System.out.println("-----------------------------------");

        Thread.sleep(7500);

        orderService.cancelOrder(1);
        System.out.println("Order cancelled: 1");
        System.out.println("-----------------------------------");

        System.out.println("Inventory:");
        for (Product inventoryProduct : inventoryService.getAllProducts()) {
            System.out.println(inventoryProduct);
        }
        System.out.println("-----------------------------------");

        System.out.println("Order History:");
        for (Order pastOrder : orderService.getAllOrders()) {
            System.out.println(pastOrder);
        }
    }
}
