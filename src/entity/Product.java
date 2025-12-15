package entity;

public class Product extends Updatable {
    private int productId;
    private String name;
    private double listPrice;
    private int stock;
    private long version;

    private Product(int productId, String name, double listPrice, int stock, long version) {
        this.productId = productId;
        this.name = name;
        this.listPrice = listPrice;
        this.stock = stock;
        this.version = version;
    }

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
                ", listPrice=" + listPrice +
                ", stock=" + stock +
                ", version=" + version +
                '}';
    }

    public Product copy() {
        return new Product(this.productId, this.name, this.listPrice, this.stock, this.version);
    }
}
