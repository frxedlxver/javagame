package item;

public class ItemEquipment extends Item {

    protected double weight;
    protected boolean equipped;

    public ItemEquipment(String id, String name, double weight) {
        super(id, name);

        this.weight = weight;
        this.equipped = false;
    }

    public void setEquipped() {
        this.equipped = true;
    }

    public void setUnequipped() {
        this.equipped = false;
    }

    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return this.name;
    }
}
