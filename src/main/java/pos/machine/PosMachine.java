package pos.machine;

import products.Product;

import java.util.*;

public class PosMachine {
    public static List<Product> ITEM_INFOS = ItemDataLoader.loadAllItemInfos();

    static List<Product> getItemInfo(List<String> barcodes) {
        List<Product> test = ItemDataLoader.loadAllItemInfos();
        List<Product> result = new ArrayList<>();
        for (String barcode : barcodes) {
            for (Product item : test) {
                if (item.getBarcode().equals(barcode))
                    result.add(item);
            }
        }
        return result;
    }

    static Map<String, Integer> countItem(List<Product> itemInfoList) {
        Map<String, Integer> result = new HashMap();
        for (Product i : itemInfoList) {
            if (!result.containsKey(i.getName())) {
                result.put(i.getName(), 1);
            } else {
                result.put(i.getName(), result.get(i.getName()) + 1);
            }
        }
        return result;
    }

    static Map<String, List<Integer>> calculateSubTotal(List<Product> itemInfoList) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (Product i : itemInfoList) {
            if (!result.containsKey(i.getName())) {
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(i.getPrice());
                temp.add(i.getPrice());
                result.put(i.getName(), temp);
            } else {
                List<Integer> temp = result.get(i.getName());
                temp.set(1, temp.get(1) + i.getPrice());
                result.put(i.getName(), temp);
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
        Integer total = 0;
        String str = "***<store earning no money>Receipt***\n";

        for (Map.Entry<String, ItemInfo> item : itemCountAndSubTotal.entrySet()) {
            total += item.getValue().getSubTotal();
            str += "Name: " + item.getKey() + ", Quantity: " + item.getValue().getQuantity() + ", Unit price: " + item.getValue().getUnitPrice() + " (yuan), Subtotal: " + item.getValue().getSubTotal() + " (yuan)\n";
        }
        str += "----------------------\n";
        str += "Total: " + total + " (yuan)\n";
        str += "**********************";
        return str;
    }
}
