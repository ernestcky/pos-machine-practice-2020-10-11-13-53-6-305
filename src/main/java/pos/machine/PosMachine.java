package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {

    private static ItemDetails generateItemDetail(List<ItemInfo> itemInfoList, String barcode) {
        ItemInfo item = itemInfoList.stream().filter(ItemInfo -> ItemInfo.getBarcode().equals(barcode)).findFirst().get();
        return new ItemDetails(item.getName(), item.getBarcode(), 1, item.getPrice(), 0);
    }

    private static List<ItemDetails> countItems(List<String> barcodes, List<ItemDetails> itemDetailsList) {
        itemDetailsList.forEach(itemInfo -> {
            itemInfo.setQuantity(Collections.frequency(barcodes, itemInfo.getBarcode()));
            itemInfo.setSubTotal(itemInfo.getQuantity() * itemInfo.getUnitPrice());
        });
        return itemDetailsList;
    }

    public static List<ItemDetails> getItemInfo(List<String> barcodes) {
        List<ItemDetails> itemDetailsList = new ArrayList<>();
        List<ItemInfo> ITEM_INFOS = ItemDataLoader.loadAllItemInfos();
        List<String> uniqueBarcodes = barcodes.stream().distinct().collect(Collectors.toList());
        uniqueBarcodes.forEach(barcode -> itemDetailsList.add(generateItemDetail(ITEM_INFOS, barcode)));
        return countItems(barcodes, itemDetailsList);
    }

    String generateMessage(List<String> barcodeList) {
        StringBuilder message = new StringBuilder();
        List<ItemDetails> itemInfoDetails = getItemInfo(barcodeList);
        int total = itemInfoDetails.stream().mapToInt(ItemDetails::getSubTotal).sum();

        itemInfoDetails.stream().forEach(
                itemDetails -> message.append(
                        String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n", itemDetails.getName(), itemDetails.getQuantity(), itemDetails.getUnitPrice(), itemDetails.getSubTotal()))
        );

        message.append("----------------------\n");
        message.append("Total: ").append(total).append(" (yuan)\n");
        message.append("**********************");

        return message.toString();
    }

    public String printReceipt(List<String> barcodes) {
        StringBuilder receipt = new StringBuilder("***<store earning no money>Receipt***\n");

        receipt.append(generateMessage(barcodes));

        return receipt.toString();
    }
}
