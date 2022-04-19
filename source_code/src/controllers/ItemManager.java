package controllers;

import item.Armament;
import item.Armor;
import item.Consumable;
import item.Item;
import my_utils.CSVReader;

import java.util.*;

public class ItemManager {

    private final HashMap<String, ArrayList<String>> itemData;

    public ItemManager() {
        this.itemData = CSVReader.buildDataArray("resources/items.csv");
    }

    public Item getItem(String itemId) {
        ArrayList<String> itemInfo = itemData.get(itemId);

        return switch (itemId.charAt(0)) {
            case 'A' -> new Armor(itemInfo);
            case 'W' -> new Armament(itemInfo);
            case 'C' -> new Consumable(itemInfo);
            default -> null;
        };
    }

    public Object getItemNameById(String itemId) {
        return itemData.get(itemId).get(1);
    }
}
