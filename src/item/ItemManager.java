package src.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ItemManager {

    private final HashMap<String, ArrayList<String>> itemData;

    public ItemManager() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("res/items.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.itemData = new HashMap<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            // skip over header rows
            if (!line.contains("HEAD")) {
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(line.split(",")));

                String itemId = temp.get(0);

                itemData.put(itemId, temp);
            }
        }
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
}
