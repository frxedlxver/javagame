package item;

import item.Item;
import item.Armament;
import item.Armor;
import item.Consumable;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {

    private final ArrayList<Consumable> consumeInv;
    private final ArrayList<Armament> armamentInv;
    private final ArrayList<Armor> armorInv;
    private int ammoPouch;

    public Inventory(){

        this.consumeInv = new ArrayList<>();
        this.armamentInv = new ArrayList<>();
        this.armorInv = new ArrayList<>();
        this.ammoPouch = 0;

    }

    /* ==================== CONTENT MANAGEMENT ==================== */
    public void addItem(Item item) {    }
    public void addItem(Armament item){
        armamentInv.add(item);
    }
    public void addItem(Armor item){
        armorInv.add(item);
    }
    public void addItem(Consumable item){
        consumeInv.add(item);
    }
    public void addAmmo(int amt) {
        ammoPouch += amt;
    }

    public void remove(Armament arm) {
        armamentInv.remove(arm);
    }
    public void remove(Armor toRemove) {
        armorInv.remove(toRemove);
    }
    public void remove(Consumable toRemove) {
        consumeInv.remove(toRemove);
    }

    /* ==================== GETTERS ==================== */

    public Consumable getConsumable(int key) {
        return consumeInv.get(key);
    }

    public Armament getWeapon (int key) {
        return armamentInv.get(key);
    }

    public Armor getArmor(int key) {
        return armorInv.get(key);
    }

    public int getAmmoCount() {
        return ammoPouch;
    }

}
