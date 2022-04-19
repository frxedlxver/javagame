package controllers;

import world.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Map implements Serializable {

    private final HashMap<String, Room> rooms;
    private Room curRoom;

    public Map() {
        this.rooms = new HashMap<>();
        this.init();
    }

    public void init() {
        Scanner sc = null;

        File mapData = new File("resources/rooms.csv");

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

    public Room getRoom(String locationId) {
        return rooms.get(locationId);
    }

    public void updateRoom(Room r) {
        rooms.replace(r.getRoomId(), r);
    }

    public void setCurRoom(String locationId) {
        this.curRoom = rooms.get(locationId);
    }
}
