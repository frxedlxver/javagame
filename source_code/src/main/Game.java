package main;

import controllers.NPCManager;
import actor.NonPlayerChar;
import actor.PlayerChar;
import controllers.GameController;
import controllers.Map;
import item.Item;
import controllers.ItemManager;
import my_utils.Help;
import my_utils.Input;
import my_utils.SaveData;
import my_utils.TimedPrint;
import world.Room;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Game implements Serializable {

    private final ItemManager itemManager;
    private final GameController controller;
    private final Map map;
    private final NPCManager npcManager;
    private final PlayerChar player;


    private GameState gameState;
    private String output;
    private ArrayList<Integer> input;
    private Room curRoom;

    public Game(String playerName) {
        this.controller = new GameController();
        this.itemManager = new ItemManager();
        this.player = new PlayerChar(playerName);
        this.map = new Map();
        this.curRoom = map.getRoom("R1");
        this.gameState = GameState.NewGame;
        this.npcManager = new NPCManager();
    }

    public static Game init(Input inp) {
        int choice = inp.getChoice("Choose one:\n[1] New game\n[2] Continue\n[3] Explanation", 1, 3);

        if (choice == 2) {
            try {
                return SaveData.load();
            } catch (IOException | ClassNotFoundException e) {
                TimedPrint.print("Save data does not exist. Beginning new game.");
            }
        } else if (choice == 3) {
            TimedPrint.print(Help.mainMenu());
            return init(inp);
        }
        String playerName = inp.getString("What is your name, child?");
        return new Game(playerName);
    }

    public void gameLoop() {
        updateGame();
        while (gameState != GameState.End) {
            TimedPrint.print(output);
            getInput();
            updateGame();
            writeToManagers();
        }
    }

    private void writeToManagers() {
        map.updateRoom(curRoom);
    }

    private void getInput() {
        controller.getNewInput();
        input = controller.getCurInput();
    }

    public void updateGame() {
        switch (gameState) {
            case NewGame -> start();
            case Normal -> parseInputNormalState(input);
            case Inventory -> parseInputInventoryState(input);
            case Combat -> parseInputCombatState(input);
        }
    }

    private void start() {
        this.output = curRoom.getDescription();
        this.gameState = GameState.Normal;
    }

    public void parseInputNormalState(ArrayList<Integer> commandIds) {
        switch (commandIds.get(0)) {
            case 0 -> // invalid command
                    output = "\nThe world is silent. Try '?' if you need help\n";
            case 12 -> // help message
                    help();
            case 13 -> // print player status
                    playerStatus();
            case 14 -> // open inventory
                    gameState = GameState.Inventory;
            case 15 -> // go
                    go(commandIds);
            case 20 -> //take item
                    take(commandIds);
            case 21 -> // look around room
                    look();
            case 28 -> // fight
                    fight(commandIds);
            case 98 -> // save game
                    save();
            case 99 -> // quit game
                    confirmQuit();
            default -> // if input is valid but not available in current GameState
                    cantDoThat();
        }
    }

    private void save() {
        output = SaveData.save(this);
    }

    private void fight(ArrayList<Integer> commandIds) {
        if (commandIds.size() != 2 || commandIds.get(1) == 0 || commandIds.get(1) > curRoom.getNpcIds().size()) {
            output = "fight who?";
        } else {
            NonPlayerChar curNPC = npcManager.loadNPC(curRoom.getNpcId(commandIds.get(1)));
            output = "Engaged battle with" + curNPC;
        }
    }

    private void confirmQuit() {
        boolean quit = controller.yesNo("\nAre you sure you want to quit? (Y | N)\n");
        // if player confirms, set output to sentinel value that ends the game
        if (quit) {
            boolean save = controller.yesNo("\nWould you like to save? (Y | N)\n");
            if (save) {
                save();
            }
            gameState = GameState.End;
        }

        // if player does not choose to quit, game will print same output as before typing quit
    }

    private void playerStatus() {
        output = player.getStatus();
    }

    private void parseInputCombatState(ArrayList<Integer> input) {
        // todo: unfinished
    }

    private void parseInputInventoryState (ArrayList<Integer> input) {
        // todo: unfinished
    }

    private void take (ArrayList<Integer> commandIds) {
        if (takeInputIsInvalid(commandIds)) {
            output = "Take what?";
        } else {
            if (commandIds.get(1) == 11) {
                for (String itemId : curRoom.getItemIds()) {
                    Item item = itemManager.getItem(itemId);
                    player.takeItem(item);
                }
                curRoom.removeAllItems();
                output = "\nPicked up all items.";
            } else {
                String itemId = curRoom.getItemId(commandIds.get(1));
                Item item = itemManager.getItem(itemId);

                player.takeItem(item);
                curRoom.removeItem(itemId);

                output = "\nPicked up " + item.toString() + ".\n";

                if (curRoom.containsItems()) {
                    output += "\nThere are some items remaining.\n";
                }
            }
        }
    }

    // return true if too many commands, invalid command, or invalid item selection.
    private boolean takeInputIsInvalid(ArrayList<Integer> commandIds) {
        int choice = commandIds.get(1);
        int all = 11;
        return (commandIds.size() != 2 || choice == 0 || (choice > curRoom.getItemCount() && choice != all));
    }

    private void go(ArrayList<Integer> commandIds) {
        if (commandIds.size() != 2) {
            output = "Go where?"; // only accept one direction as valid input
        } else {
            // get direction
            String nextRoomId = "none";
            switch (commandIds.get(1)) {
                case 16 -> {
                    nextRoomId = curRoom.getNorthExitId();
                    output = "You go north.\n";
                }
                case 17 -> {
                    nextRoomId = curRoom.getEastExitId();
                    output = "You go east.\n";
                }
                case 18 -> {
                    nextRoomId = curRoom.getSouthExitId();
                    output = "You go south.\n";
                }
                case 19 -> {
                    nextRoomId = curRoom.getWestExitId();
                    output = "You go west.\n";
                }
            }
            // if player tries to travel to an exit that does not exist
            // eg. player enters 'go north' but room has only west exit.
            if (nextRoomId.equals("none")) {
                output = "Can't go that way.";
            } else {
                writeToManagers();
                curRoom = map.getRoom(nextRoomId);
                output += curRoom.getDescription();
            }
        }
    }

    private void look() {
        output = "You look around.";
        if (curRoom.containsItems()) {
            output += "The room contains some items: \n" + curRoom.listItems(itemManager);
        }
        output += curRoom.listExits();
    }

    public void cantDoThat() {
        System.out.println("Can't do that right now");
    }

    public void help() {
        output = switch (gameState) {
            case Normal -> Help.normal();
            case Inventory -> Help.inventory();
            case Combat -> Help.combat();
            default -> "\nthis is the help menu\n";
        };
    }

    public enum GameState {
        Combat, End, Inventory, NewGame, Normal, Menu
    }
}
