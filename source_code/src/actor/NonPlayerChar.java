package actor;

import java.util.ArrayList;

public class NonPlayerChar extends Actor {

    private double att;

    public NonPlayerChar (ArrayList<String> NPCInfo) {
        super(NPCInfo.get(0), NPCInfo.get(1), Double.parseDouble(NPCInfo.get(2)));
        this.att = Double.parseDouble(NPCInfo.get(4));
    }

    public void adjustHealth(double amt) {
        this.hpCur += amt;
    }

    public double getAtt() {
        return this.att;
    }
}
