package products;

public class Sprite extends Product {
    private String barcode = "ITEM000001";
    private int price = 3;
    private String name;

    public Sprite() {
        barcode = "ITEM000001";
        price = 3;
        name = "Sprite";
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
