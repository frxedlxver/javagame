package actor;

import item.*;

import java.io.Serializable;

public class PlayerChar extends Actor implements Serializable {

    // instance variables
    private int level;
    private int exp;
    private int expToNext;
    private double str;

    private double curEquipWeight;
    private double maxEquipWeight;

    private final Inventory inventory;

    private ItemEquipment eqArmor;
    private ItemEquipment eqWeaponLeft;
    private ItemEquipment eqWeaponRight;

    private String curLocationId;



    // constructor method
    public PlayerChar(String name) {

        // init superclass
        super(name, "P1", 25);

        // set default stats
        this.level = 1;
        this.exp = 0;
        this.expToNext = 100;
        this.str = 5;
        this.curEquipWeight = 0;
        this.maxEquipWeight = 20;

        // initialize inventory
        this.inventory = new Inventory();

        // initialize location
        this.curLocationId = "R1";

    }

    /* ==================== CHARACTER MANAGEMENT ==================== */

    // equipment management
    public void equipLeft(Armament weapon) {
        equip(0, weapon);
    }

    public void equipRight(Armament weapon) {
        equip(1, weapon);
    }

    public void equipArmor(Armor armor) {
        equip(2, armor);
    }

    private void equip(int slot, ItemEquipment toEquip) {

        // slot: 1 = left weapon; 2 = right weapon; 3 = armor.
        ItemEquipment slotToUpdate = switch (slot) {
            case 0 -> this.eqWeaponLeft;
            case 1 -> this.eqWeaponRight;
            case 2 -> this.eqArmor;
            default -> null;
        };

        // if something is already equipped
        if (slotToUpdate != null) {
            slotToUpdate.setUnequipped();
        }

        slotToUpdate = toEquip;

        if (toEquip != null) {
            slotToUpdate.setEquipped();
            inventory.removeEquipment(toEquip);
        }

        this.updateEquipWeight();
    }

    private void updateEquipWeight() {
        this.curEquipWeight = eqWeaponLeft.getWeight() + eqWeaponRight.getWeight() + eqArmor.getWeight();
    }

    private void unequip(int slot) {
        equip(3, null);
    }

    public void unequipLeft() {
        this.eqWeaponLeft.setUnequipped();
        this.inventory.addItem(eqWeaponLeft);
        this.eqWeaponLeft = null;
        this.updateEquipWeight();
    }

    public void unequipRight() {
        this.eqWeaponRight.setUnequipped();
        this.inventory.addItem(eqWeaponRight);
        this.eqWeaponRight = null;
        this.updateEquipWeight();
    }

    public void unequipArmor() {
        this.eqArmor.setUnequipped();
        this.inventory.addItem(eqArmor);
        this.updateEquipWeight();
    }


    // mutator methods for stat changes
    public void gainExp(int amt) {
        if (exp + amt >= expToNext) {
            this.incrementLevel();
            exp += amt - expToNext;
            expToNext += expToNext * 0.1;
        } else {
            exp += amt;
        }
    }

    public void incrementLevel() {
        level++;

        // update simple stats
        hpCur = hpTot = hpTot + 3;
        str += 1;
        maxEquipWeight += 1;
    }

    public void adjustHealth(int amt) {
        this.hpCur += amt;
        if(this.hpCur > hpTot) {
            hpCur = hpTot;
        }
    }

    /* ==================== GETTERS ==================== */

    public String getLocationId() {
        return curLocationId;
    }

    public double getDef() {
        return (str * 2) + eqArmor.getDefRating();
    }

    public double getBlockLeft() {
        return eqWeaponLeft.getBlockRating();
    }

    public double getBlockRight() {
        return eqWeaponRight.getBlockRating();
    }

    public double getAttLeft() {
        return (str * 3) + 10 + eqWeaponLeft.getAttRating();
    }

    public double getAttRight() {
        return (str * 3) + 10 + eqWeaponRight.getAttRating();
    }

    public Armament getEqWeaponLeft() {
        return eqWeaponLeft;
    }

    public Armament getEqWeaponRight() {
        return eqWeaponRight;
    }

    public Armor getEqArmor() {
        return eqArmor;
    }

    /* ==================== SETTERS ==================== */

    public void moveTo(String locationId) {
        this.curLocationId = locationId;
    }

    public void takeItem(Item item) {
        inventory.addItem(item);
    }

    public void takeAmmo(int amt) {
        inventory.addAmmo(amt);
    }

    /* ==================== STRING ==================== */

    public String getStatus() {
        return String.format("""
                
                ==== %s ====
                Level: %d
                EXP to next: %d
                HP: %.0f / %.0f
                STR: %.1f
                
                ==== Equipment ====
                Equip load: %.1f / %.1f
                Left Hand: %s
                Right Hand: %s
                Ammo count: %d
                Armor: %s
                """, name, level, expToNext, hpCur, hpTot, str, curEquipWeight, maxEquipWeight, eqWeaponLeft,
                eqWeaponRight, inventory.getAmmoCount(), eqArmor);

    }

    public String toString() {
        return name;
    }

    public void giveAmmo(int effectAmount) {
        inventory.addAmmo(effectAmount);
    }
}
