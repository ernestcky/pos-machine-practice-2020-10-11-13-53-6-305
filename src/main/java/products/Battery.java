package products;

public class Battery extends Product {
    private String barcode;
    private int price;
    private String name;

    public Battery() {
        barcode = "ITEM000004";
        price = 2;
        name = "Battery";
    }

    @Override
    public String getBarcode() {
        return barcode;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
