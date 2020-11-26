package pos.machine;

import products.Product;

import java.util.*;

public class PosMachine {

    static List<Product> getItemInfo(List<String> barcodes) {
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

    static Map<String, Integer> countItem(List<Product> itemInfoList) {
        Map<String, Integer> result = new HashMap();
        for (Product product : itemInfoList) {
            if (!result.containsKey(product.getName())) {
                result.put(product.getName(), 1);
            } else {
                result.put(product.getName(), result.get(product.getName()) + 1);
            }
        }
        return result;
    }

    static Map<String, List<Integer>> calculateSubTotal(List<Product> itemInfoList) {
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

    static Map<String, ItemInfo> countItemAndCalculateSubtotal(List<Product> itemInfoList) {
        Map<String, ItemInfo> result = new HashMap<>();
        Map<String, Integer> countMap = countItem(itemInfoList);
        Map<String, List<Integer>> subTotalMap = calculateSubTotal(itemInfoList);

        for (Map.Entry<String, Integer> count : countMap.entrySet()) {
            if (!result.containsKey(count.getKey())) {
                ItemInfo info = new ItemInfo(count.getValue(), subTotalMap.get(count.getKey()).get(0), subTotalMap.get(count.getKey()).get(1));
                result.put(count.getKey(), info);
            }
        }

        return result;
    }

    public static String printReceipt(List<String> barcodes) {
        List<Product> itemInfoList = getItemInfo(barcodes);
        Map<String, ItemInfo> itemCountAndSubTotal = countItemAndCalculateSubtotal(itemInfoList);
        int total = 0;
        StringBuilder str = new StringBuilder("***<store earning no money>Receipt***\n");

        for (Map.Entry<String, ItemInfo> item : itemCountAndSubTotal.entrySet()) {
            total += item.getValue().getSubTotal();
            str.append("Name: ").append(item.getKey()).append(", Quantity: ").append(item.getValue().getQuantity()).append(", Unit price: ").append(item.getValue().getUnitPrice()).append(" (yuan), Subtotal: ").append(item.getValue().getSubTotal()).append(" (yuan)\n");
        }
        str.append("----------------------\n");
        str.append("Total: ").append(total).append(" (yuan)\n");
        str.append("**********************");
        return str.toString();
    }
}
