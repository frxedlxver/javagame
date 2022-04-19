package actor;

import java.io.Serializable;
import java.util.ArrayList;

public class Actor implements Serializable {

    protected String id;
    protected String name;
    protected double hpTot;
    protected double hpCur;
    protected boolean dead;

    public Actor(String name, String id, double hpTot) {
        this.name = name;
        this.hpCur = this.hpTot = hpTot;
        this.dead = false;
    }
}
