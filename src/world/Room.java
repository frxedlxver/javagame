package src.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Room {

    private final String roomID;
    private final ArrayList<String> itemIDs;
    private final ArrayList<String> actorIDs;
    private final String northExitID;
    private final String eastExitID;
    private final String southExitID;
    private final String westExitID;
    private final String description;
    private boolean visited;

    public Room(String[] roomInfo) {
        this.visited = false;

        this.roomID = roomInfo[0];

        this.itemIDs = new ArrayList<>(Arrays.asList(roomInfo[1].split(" ")));

        this.actorIDs = new ArrayList<>();
        if (roomInfo[2].length() > 0) {
            this.actorIDs.addAll(Arrays.asList(roomInfo[2].split(" ")));
        }

        this.northExitID = roomInfo[3];
        this.eastExitID = roomInfo[4];
        this.southExitID = roomInfo[5];
        this.westExitID = roomInfo[6];

        this.description = loadDescription("res/room_data/" + roomID + ".txt");


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
        return this.itemIDs.size() > 0;
    }

    public boolean hasActor() {
        return this.actorIDs.size() >0;
    }

    public String getRoomID() {
        return roomID;
    }

    public ArrayList<String> getItemIDs() {
        return itemIDs;
    }

    public ArrayList<String> getActorIDs() {
        return actorIDs;
    }

    public String getNorthExitID() {
        return northExitID;
    }

    public String getEastExitID() {
        return eastExitID;
    }

    public String getSouthExitID() {
        return southExitID;
    }

    public String getWestExitID() {
        return westExitID;
    }

    public String getDescription() {
        return description;
    }

    /* ==================== SETTERS ==================== */

    public void setVisited() {
        this.visited = true;
    }

    public boolean containsItems() {
        return this.itemIDs.size() > 0;
    }

    public void removeItem(String itemId) {
        itemIDs.remove(itemId);
    }

    public String listExits() {
        ArrayList<String> temp = new ArrayList<>();

        if (!northExitID.equals("none")) {
            temp.add("north");
        }
        if (!eastExitID.equals("none")) {
            temp.add("east");
        }
        if (!southExitID.equals("none")) {
            temp.add("south");
        }
        if (!westExitID.equals("none")) {
            temp.add("west");
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < temp.size(); i++) {
            sb.append(temp.get(i));
            if (i == temp.size() - 2) {
                sb.append(", and ");
            } else if (i != temp.size() -1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public int countExits() {
        int result = 0;

        if (!northExitID.equals("none")) {
            result += 1;
        }
        if (!eastExitID.equals("none")) {
            result += 1;
        }
        if (!southExitID.equals("none")) {
            result += 1;
        }
        if (!westExitID.equals("none")) {
            result += 1;
        }

        return result;
    }
}
