import entity.Order;
import entity.OrderItem;
import entity.Product;
import entity.User;
import service.AuthService;
import service.InventoryService;
import service.OrderService;

import java.util.List;

public class ConcurrentTest {

    public static void runTests(AuthService authService, InventoryService inventoryService, OrderService orderService) {
        authService.signUp("amankr8", "pass1234");
        User user = authService.signIn("amankr8", "pass1234");

        inventoryService.addProduct(new Product("Black T-shirt", 500.0, 5));
        List<Product> inventory = inventoryService.getAllProducts();
        Product randomProduct = inventory.getFirst();

        Runnable placeOrderTask = () -> {
            try {
                orderService.placeOrder(new Order(user.getUserId(), List.of(new OrderItem(randomProduct.getProductId(), 2, randomProduct.getPrice()))));
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
            }
        };

        Thread thread1 = new Thread(placeOrderTask, "T1");
        Thread thread2 = new Thread(placeOrderTask, "T2");
        Thread thread3 = new Thread(placeOrderTask, "T2");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println("Final stock of product " + randomProduct.getProductId() + ": " + inventoryService.getProductById(randomProduct.getProductId()).getStock());
    }
}
