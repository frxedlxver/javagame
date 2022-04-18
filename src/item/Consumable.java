package src.item;

import src.actor.PlayerChar;

import java.util.ArrayList;

public class Consumable extends Item {

    private String effect;
    private int effectAmount;

    public Consumable(ArrayList<String> itemInfo) {
        super(itemInfo.get(0), itemInfo.get(1));
        this.effect = itemInfo.get(2);
        this.effectAmount = Integer.parseInt(itemInfo.get(3));

    }

    public void use(PlayerChar pc) {
        if (effect.equalsIgnoreCase("heal")) {
            pc.adjustHealth(effectAmount);
        } else if (effect.equalsIgnoreCase("ammo")) {
            pc.giveAmmo(effectAmount);
        }
    }

}
