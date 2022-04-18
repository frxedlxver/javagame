package src.actor;

public class Actor {

    protected int id;
    protected String name;
    protected double hpTot;
    protected double hpCur;
    protected boolean dead;

    public Actor(String name, double hpTot) {
        this.name = name;
        this.hpCur = this.hpTot = hpTot;
        this.dead = false;
    }
}
