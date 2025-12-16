package entity;

import java.util.List;

public class Order extends Updatable {
    private int orderId;
    private int userId;
    private double totalAmount;
    private List<OrderItem> orderItems;
    private OrderStatus status;
    private long version;

    private Order(int orderId, int userId, double totalAmount, List<OrderItem> orderItems, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.status = status;
    }

    public Order(int userId, List<OrderItem> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.status = OrderStatus.PLACED;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                super.toString() +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                ", status=" + status +
                '}';
    }

    public Order copy() {
        return new Order(this.orderId,
                this.userId,
                this.totalAmount,
                this.orderItems.stream().map(OrderItem::copy).toList(),
                this.status
        );
    }
}
