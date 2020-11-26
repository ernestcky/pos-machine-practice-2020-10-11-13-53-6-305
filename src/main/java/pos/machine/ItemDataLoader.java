package pos.machine;

import products.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemDataLoader {
    public static List<Product> loadAllItemInfos() {

        List<Product> itemInfos = new ArrayList<>();
        itemInfos.add(new CocaCola());
        itemInfos.add(new Sprite());
        itemInfos.add(new Battery());

        return itemInfos;
    }

    public static List<String> loadBarcodes() {
        return Arrays.asList("ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000001", "ITEM000001", "ITEM000004", "ITEM000004", "ITEM000004");
    }
}
