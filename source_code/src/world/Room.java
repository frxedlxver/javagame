package world;

import controllers.ItemManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Room {

    private final String roomId;
    private final ArrayList<String> itemIds;
    private final ArrayList<String> npcIds;
    private final HashMap<String, String> exitIds;
    private final String description;
    private boolean visited;

    public Room(String[] roomInfo) {
        this.visited = false;

        this.roomId = roomInfo[0];

        this.itemIds = new ArrayList<>(Arrays.asList(roomInfo[1].split(" ")));

        this.npcIds = new ArrayList<>();
        if (roomInfo[2].length() > 0) {
            this.npcIds.addAll(Arrays.asList(roomInfo[2].split(" ")));
        }

        this.exitIds = new HashMap<>();
        exitIds.put("north", roomInfo[3]);
        exitIds.put("east", roomInfo[4]);
        exitIds.put("south", roomInfo[5]);
        exitIds.put("west", roomInfo[6]);

        this.description = loadDescription("resources/room_data/" + roomId + ".txt");
    }

    private String loadDescription(String s) {
        try {
            Scanner sc = new Scanner(new File(s));
            StringBuilder sb = new StringBuilder();

            while (sc.hasNext()) {
                sb.append(sc.nextLine()).append("\n");
            }

            return sb.toString().trim();
        } catch (FileNotFoundException e) {
            return "empty room";
        }
    }
    /* ==================== GETTERS ==================== */
    public boolean isVisited() {
        return visited;
    }

    public boolean hasItem() {
        return this.itemIds.size() > 0;
    }

    public boolean hasActor() {
        return this.npcIds.size() >0;
    }

    public String getRoomId() {
        return roomId;
    }

    public ArrayList<String> getItemIds() {
        return itemIds;
    }

    public ArrayList<String> getNpcIds() {
        return npcIds;
    }

    public String getNorthExitId() {
        return exitIds.get("north");
    }

    public String getEastExitId() {
        return exitIds.get("east");
    }

    public String getSouthExitId() {
        return exitIds.get("south");
    }

    public String getWestExitId() {
        return exitIds.get("west");
    }

    public String getDescription() {
        return description;
    }

    /* ==================== SETTERS ==================== */

    public void setVisited() {
        this.visited = true;
    }

    public boolean containsItems() {
        return !this.itemIds.get(0).equals("none");
    }

    public void removeItem(String itemId) {
        itemIds.remove(itemId);
    }

    // method to return a formatted string to tell user what directions it is possible to travel from current position
    public String listExits() {

        // remove direction from list if exitIds does not contain a valid ID associated with that direction
        // https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#removeIf-java.util.function.Predicate-
        ArrayList<String> temp = new ArrayList<>(Arrays.asList("north", "east", "south", "west"));
        temp.removeIf(s -> exitIds.get(s).equals("none"));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            result.append(temp.get(i));
            // separate all elements with comma
            if (i < temp.size() - 1) {
                result.append(", ");
            }
            // separate last and second-to-last element with and
            if (i == temp.size() - 2) {
                result.append("and ");
            }
        }

        if (temp.size() == 1) {
            return "\nThe room has one exit, pointing " + result + ".";
        } else {
            return "\nThe room has " + result + " exits.";
        }
    }

    public String listItems(ItemManager im) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String itemId : this.getItemIds()) {
            sb.append(String.format("%d: %s", i, im.getItemNameById(itemId)));
            i++;
        }
        return sb.toString();
    }

    public String getItemId(int i) {
        return itemIds.get(i);
    }

    public int getItemCount() {
        return itemIds.size();
    }

    public String getNpcId(int i) {
        return npcIds.get(i);
    }

    public int getNpcCount() {
        return npcIds.size();
    }

    public void removeAllItems() {
        while (itemIds.size() > 0) {
            itemIds.remove(0);
        }
        itemIds.add("none");
    }
}
