package pos.machine;

import products.Product;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {

    List<Product> getItemInfo(List<String> barcodes) {
        List<Product> itemInfos = ItemDataLoader.loadAllItemInfos();
        List<Product> result = new ArrayList<>();
        for (String barcode : barcodes) {
            for (Product item : itemInfos) {
                if (item.getBarcode().equals(barcode))
                    result.add(item);
            }
        }
        return result;
    }

    void countItem(Map<Product, Integer> countMap, List<Product> itemInfoList) {
        itemInfoList.stream().forEach(
                item -> {
                    if (countMap.containsKey(item)) {
                        countMap.put(item, countMap.get(item) + 1);
                    } else {
                        countMap.put(item, 1);
                    }
                }
        );
    }

    Map<String, List<Integer>> calculateSubTotal(List<Product> itemInfoList) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (Product product : itemInfoList) {
            if (!result.containsKey(product.getName())) {
                List<Integer> unitPriceAndSubtotal = new ArrayList<Integer>();
                unitPriceAndSubtotal.add(product.getPrice());
                unitPriceAndSubtotal.add(product.getPrice());
                result.put(product.getName(), unitPriceAndSubtotal);
            } else {
                List<Integer> unitPriceAndSubtotal = result.get(product.getName());
                unitPriceAndSubtotal.set(1, unitPriceAndSubtotal.get(1) + product.getPrice());
                result.put(product.getName(), unitPriceAndSubtotal);
            }
        }
        return result;
    }

    String countItemAndCalculateSubtotal(List<Product> itemInfoList) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Product, Integer> count : countMap.entrySet()) {
            result
                    .append("Name: ")
                    .append(count.getKey().getName())
                    .append(", Quantity: ")
                    .append(count.getValue())
                    .append(", Unit price: ")
                    .append(count.getKey().getPrice())
                    .append(" (yuan), Subtotal: ")
                    .append(count.getKey().getPrice() * count.getValue()).append(" (yuan)\n");

        }
        return result.toString();
    }

    public String printReceipt(List<String> barcodes) {
        List<Product> itemInfoList = getItemInfo(barcodes);
        int total = 0;
        StringBuilder str = new StringBuilder("***<store earning no money>Receipt***\n");

        str.append(countItemAndCalculateSubtotal(itemInfoList));
        str.append("----------------------\n");
        str.append("Total: ").append(total).append(" (yuan)\n");
        str.append("**********************");
        return str.toString();
    }
}
