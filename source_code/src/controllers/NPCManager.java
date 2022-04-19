package controllers;


import actor.NonPlayerChar;
import my_utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;

public class NPCManager {

    private final HashMap<String, ArrayList<String>> NPCData;
    private final HashMap<String, NonPlayerChar> loadedNPCs;

    public NPCManager() {
        this.NPCData = CSVReader.buildDataArray("resources/npc.csv");
        loadedNPCs = new HashMap<>();
    }

    public NonPlayerChar loadNPC(String actorId) {
        if (!loadedNPCs.containsKey(actorId)) {
            loadedNPCs.put(actorId, new NonPlayerChar(NPCData.get(actorId)));
        }
        return loadedNPCs.get(actorId);
    }
}
