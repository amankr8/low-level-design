package entity;

public class Product extends Updatable {
    private int productId;
    private String name;
    private Double price;
    private int stock;
    private long version;

    private Product(int productId, String name, Double price, int stock, long version) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.version = version;
    }

    public Product(String name, Double price, int stock) {
        this.name = name;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                super.toString() +
                ", name='" + name + '\'' +
                ", listPrice=" + price +
                ", stock=" + stock +
                ", version=" + version +
                '}';
    }

    public Product copy() {
        return new Product(this.productId, this.name, this.price, this.stock, this.version);
    }
}
