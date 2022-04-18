package src;

import src.world.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Map {

    private final HashMap<String, Room> rooms;
    private Room curRoom;

    public Map() {
        this.rooms = new HashMap<>();
        this.init();
    }

    public void init() {
        Scanner sc = null;

        File mapData = new File("res/rooms.csv");

        try {
            sc = new Scanner(mapData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // skip header line
        sc.nextLine();
        while (sc.hasNext()) {
            String[] temp = sc.nextLine().split(",");
            rooms.put(temp[0], new Room(temp));
        }

        this.curRoom = rooms.get("R1");
    }

    public Room getRoom(String locationID) {
        return rooms.get(locationID);
    }

    public Room getCurRoom() {
        return curRoom;
    }

    public void setCurRoom(String locationID) {
        this.curRoom = rooms.get(locationID);
    }
}
