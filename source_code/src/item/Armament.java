package item;

import java.util.ArrayList;

public class Armament extends ItemEquipment {

    private final String damageType;
    private final String weaponType;
    protected double attRating;
    protected double blockRating;
    protected int upgradeLevel;
    private final int ammoCost;

    public Armament(ArrayList<String> itemInfo) {
        // init superclass with id, name, weight
        super(itemInfo.get(0), itemInfo.get(2), Double.parseDouble(itemInfo.get(6)));

        this.weaponType = itemInfo.get(1);
        this.attRating = Double.parseDouble(itemInfo.get(3));
        this.blockRating = Double.parseDouble(itemInfo.get(4));
        this.damageType = itemInfo.get(5);
        this.ammoCost = Integer.parseInt(itemInfo.get(7));
        this.upgradeLevel = 0;
    }

    public double getAttRating(){
        return attRating;
    }

    public double getBlockRating() {
        return blockRating;
    }

    public String getDamageType() {
        return damageType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public int getAmmoCost() {
        return ammoCost;
    }
}
