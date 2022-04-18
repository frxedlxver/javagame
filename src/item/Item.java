package src.item;

public class Item {

    protected String name;
    protected String id;

    public Item(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

    public String toString() { return this.name; }
}

