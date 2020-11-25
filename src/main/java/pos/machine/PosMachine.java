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
                if (item.barcode.equals(barcode))
                    result.add(item);
            }
        }
        return result;
    }

    static Map<String, Integer> countItem(List<Product> itemInfoList) {
        Map<String, Integer> result = new HashMap();
        for (Product i : itemInfoList) {
            if (!result.containsKey(i.getClass().getName())) {
                result.put(i.getClass().getName(), 1);
            }
            else {
                result.put(i.getClass().getName(), result.get(i.getClass().getName()) + 1);
            }
        }
        return result;
    }

    static Map<String, List<Integer>> calculateSubTotal(List<Product> itemInfoList) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (Product i : itemInfoList) {
            if (!result.containsKey(i.getClass().getName())) {
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(i.price);
                temp.add(i.price);
                result.put(i.getClass().getName(), temp);
            }
            else {
                List<Integer> temp = result.get(i.getClass().getName());
                temp.set(1, temp.get(1) + i.price);
                result.put(i.getClass().getName(), temp);
            }
        }
        return result;
    }


    static Map<String, List<Integer>> countItemAndCalculateSubtotal(List<Product> itemInfoList) {
        Map<String, List<Integer>> result = new HashMap<>();
        Map<String, Integer> countMap = countItem(itemInfoList);
        Map<String, List<Integer>> subTotalMap = calculateSubTotal(itemInfoList);

        for (Map.Entry<String, Integer> c : countMap.entrySet()) {
            if (!result.containsKey(c.getKey())) {
                List<Integer> temp = new ArrayList<>();
                temp.add(c.getValue());
                temp.add(subTotalMap.get(c.getKey()).get(0));
                temp.add(subTotalMap.get(c.getKey()).get(1));
                result.put(c.getKey(), temp);
            }
        }

        return result;
    }

    public static String printReceipt(List<String> barcodes) {
        List<Product> itemInfoList = getItemInfo(barcodes);
        Map<String, List<Integer>> itemCountAndSubTotal = countItemAndCalculateSubtotal(itemInfoList);
        Integer total = 0;
        String str = "***<store earning no money>Receipt***\n";

        for (Map.Entry<String, List<Integer>> m : itemCountAndSubTotal.entrySet()) {
            total += m.getValue().get(2);
            str += "Name: " + m.getKey() + ", Quantity: " + m.getValue().get(0) + ", Unit price: " + m.getValue().get(1) + " (yuan), Subtotal: " + m.getValue().get(2) + " (yuan)\n";
        }
        str += "----------------------\n";
        str += "Total: " + total + " (yuan)\n";
        str += "**********************";
        return str;
    }
}
