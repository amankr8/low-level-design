package entity;

import java.util.List;

public class Order extends Updatable {
    private int orderId;
    private int userId;
    private double totalAmount;
    private List<OrderItem> orderItems;
    private OrderStatus status;

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

    @Override
    public String toString() {
        return "Order{" +
                super.toString() +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                ", status=" + status +
                '}';
    }
}
