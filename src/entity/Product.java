package entity;

public class Product {
    private int productId;
    private String name;
    private double listPrice;
    private int stock;

    public Product(String name, double listPrice, int stock) {
        this.name = name;
        this.listPrice = listPrice;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", listPrice=" + listPrice +
                ", stock=" + stock +
                '}';
    }
}
