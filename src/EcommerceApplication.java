import repository.OrderRepository;
import repository.ProductRepository;
import repository.UserRepository;
import service.AuthService;
import service.InventoryService;
import service.OrderService;
import service.impl.AuthServiceImpl;
import service.impl.InventoryServiceImpl;
import service.impl.OrderServiceImpl;

public class EcommerceApplication {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthServiceImpl(userRepository);

        ProductRepository productRepository = new ProductRepository();
        InventoryService inventoryService = new InventoryServiceImpl(productRepository);

        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderServiceImpl(orderRepository, inventoryService);

        try {
            // DemoHelper.runDemo(authService, inventoryService, orderService);
            ConcurrentTest.runTests(authService, inventoryService, orderService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}