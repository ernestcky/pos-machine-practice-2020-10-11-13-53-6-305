package products;

public abstract class Product {
    private String barcode;
    private int price;

    public String getBarcode() {
        return barcode;
    }

    public int getPrice() {
        return price;
    }

    public abstract String getName();
}
