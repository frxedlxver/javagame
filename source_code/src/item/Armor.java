package item;

import item.ItemEquipment;

import java.util.ArrayList;

public class Armor extends ItemEquipment {

    private final double defRating;

    public Armor(ArrayList<String> itemInfo) {
        super(itemInfo.get(0), itemInfo.get(1), Double.parseDouble(itemInfo.get(3)));

        this.defRating = Double.parseDouble(itemInfo.get(2));
    }

    public double getDefRating() {
        return defRating;
    }

}
