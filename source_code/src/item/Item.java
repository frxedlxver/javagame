package item;

import java.io.Serializable;

public class Item implements Serializable {

    protected String name;
    protected String id;

    public Item(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String toString() { return this.name; }
}

