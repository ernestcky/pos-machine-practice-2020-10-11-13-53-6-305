package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PosMachine {
    static List<ItemInfo> ITEM_INFOS = ItemDataLoader.loadAllItemInfos();

    List<ItemInfo> getItemInfo(List<String> barcodes) {
        List<ItemInfo> result = new ArrayList<>();
        for (String barcode : barcodes) {
            for (ItemInfo item : ITEM_INFOS) {
                if (item.getBarcode().equals(barcode))
                    result.add(item);
            }
        }
        return result;
    }

    Map<String, Integer> countItem(List<ItemInfo> itemInfoList) {
        Map<String, Integer> result = new HashMap();
        for (ItemInfo i : itemInfoList) {
            if (!result.containsKey(i.getName())) {
                result.put(i.getName(), 1);
            }
            else {
                result.put(i.getName(), result.get(i.getName()) + 1);
            }
        }
        return result;
    }

    Map<String, List<Integer>> calculateSubTotal(List<ItemInfo> itemInfoList) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (ItemInfo i : itemInfoList) {
            if (!result.containsKey(i.getName())) {
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(i.getPrice());
                temp.add(i.getPrice());
                result.put(i.getName(), temp);
            }
            else {
                List<Integer> temp = result.get(i.getName());
                temp.set(1, temp.get(1) + i.getPrice());
                result.put(i.getName(), temp);
            }
        }
        return result;
    }


    Map<String, List<Integer>> countItemAndCalculateSubtotal(List<ItemInfo> itemInfoList) {
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

    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> itemInfoList = getItemInfo(barcodes);
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
