package products;

public class CocaCola extends Product {
    private String barcode;
    private int price;
    private String name;

    public CocaCola() {
        barcode = "ITEM000000";
        price = 3;
        name = "Coca-Cola";
    }

    public String getBarcode() {
        return barcode;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
