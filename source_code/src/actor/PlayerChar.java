package actor;

import item.Inventory;
import item.Armament;
import item.Armor;
import item.Item;

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

    private Armor eqArmor;
    private Armament eqWeaponLeft;
    private Armament eqWeaponRight;

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
        if (eqWeaponLeft != null) {
            unequipLeft();
        }
        this.eqWeaponLeft = weapon;
        this.eqWeaponLeft.setEquipped();
    }

    public void equipRight(Armament weapon) {
        if (eqWeaponRight != null) {
            unequipRight();
        }
        this.eqWeaponRight = weapon;
        this.eqWeaponRight.setEquipped();
    }

    public void equipArmor(Armor armor) {
        if (eqArmor != null) {
            unequipArmor();
        }
        this.eqArmor = armor;
        this.eqArmor.setEquipped();
    }

    public void unequipLeft() {
        this.eqWeaponLeft.setUnequipped();
        this.inventory.addItem(eqWeaponLeft);
        this.eqWeaponLeft = null;
    }

    public void unequipRight() {
        this.eqWeaponRight.setUnequipped();
        this.inventory.addItem(eqWeaponRight);
        this.eqWeaponRight = null;
    }

    public void unequipArmor() {
        this.eqArmor.setUnequipped();
        this.inventory.addItem(eqArmor);
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
