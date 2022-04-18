package src;

import src.my_utils.Help;
import src.my_utils.Input;
import src.actor.PlayerChar;
import src.item.Item;
import src.item.ItemManager;
import src.my_utils.TimedPrint;
import src.world.Room;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;


public class Game implements Serializable {

    public enum GameState {
        Normal,
        Menu,
        Inventory,
        NewGame, Combat
    }

    private static final String SAVE_PATH = "data/saveData.bin";

    private final ItemManager itemManager;
    private final GameController controller;
    private final PlayerChar player;
    private final Map curMap;


    private GameState gameState;
    private String output;
    private ArrayList<Integer> input;

    public Game(String playerName) {
        this.controller = new GameController();
        this.itemManager = new ItemManager();
        this.player = new PlayerChar(playerName);
        this.curMap = new Map();
        this.gameState = GameState.NewGame;
    }

    public void gameLoop() {
        updateGame();
        while (!output.equals("END")) {
            printOutput();
            getInput();
            updateGame();
        }
    }

    private void printOutput() {
        TimedPrint.print(output);
    }

    public static Game init(Input inp) {
        int choice = inp.getChoice("Choose one:\n[1] New game\n[2] Continue\n[3] Explanation", 1, 3);

        if (choice == 2) {
            try {
                return Game.load();
            } catch (IOException | ClassNotFoundException e) {
                TimedPrint.print("Save data does not exist. Beginning new game.");
            }
        } else if (choice == 3) {
            TimedPrint.print("""
                    You will interact with this game by entering commands into the console.
                    The list of commands currently accepted is not exhaustive.
                    To help with this, you can enter '?' at any timeto receive an explanation of the current game state.
                    This will include suggestions about current valid commands, and how to use them.
                    """);
            return init(inp);
        }

        String playerName = inp.getString("What is your name, child?");
        return new Game(playerName);
    }

    public static Game load() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH));
        Game g = (Game) ois.readObject();
        ois.close();
        return g;
    }



    public void save() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH));
                oos.writeObject(this);
                oos.close();
                output = "\nGame saved.\n\n" + output;
            } catch (IOException e) {
                output = "\nGame cannot be saved\n\n" + output;
            }
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
        this.output = curMap.getCurRoom().getDescription();
        this.gameState = GameState.Normal;
    }

    public void parseInputNormalState(ArrayList<Integer> commandIDs) {
        switch (commandIDs.get(0)) {
            case 0 -> // invalid command
                    output = "\nThe world is silent. Try '?' if you need help\n";
            case 1 -> // help message
                    help();
            case 2 -> // print player status
                    playerStatus();
            case 3 -> // open inventory
                    gameState = GameState.Inventory;
            case 4 -> // go
                    go(commandIDs);
            case 9 -> //take item
                    take(commandIDs);
            case 10 -> // look around room
                    look();
            case 98 -> // save game
                    save();
            case 99 -> // quit game
                    confirmQuit();
            default -> // if input is valid but not available in current GameState
                    cantDoThat();
        }
    }

    private void confirmQuit() {
        boolean quit = controller.yesNo("\nAre you sure you want to quit? (Y | N)\n");
        // if player confirms, set output to sentinel value that ends the game
        if(quit) {
            boolean save = controller.yesNo("\nWould you like to save? (Y | N)\n");
            if (save) {
                save();
            }
            output = "END";
        }

        // if player does not choose to quit, game will print same output as before typing quit
    }

    private void playerStatus() {
        output = player.getStatus();
    }

    private void parseInputCombatState(ArrayList<Integer> input) {

    }

    private void parseInputInventoryState(ArrayList<Integer> input) {

    }

    private void take(ArrayList<Integer> commandIDs) {
        if (commandIDs.size() != 2){
            output = "Take what?";
        } else {
            String itemID = curMap.getCurRoom().getItemIDs().get(commandIDs.get(1) - 21);
            Item item = itemManager.getItem(itemID);

            player.takeItem(item);
            curMap.getCurRoom().removeItem(itemID);

            output = "\nPicked up " + item.toString() + ".\n";

            if(curMap.getCurRoom().containsItems()) {
                output += "\nThere are some items remaining:\n" + listItems(curMap.getCurRoom());                ;
            }
        }
    }

    private void go(ArrayList<Integer> commandIDs) {
        if (commandIDs.size() != 2){
            output = "Go where?";
        } else {
            String locID = "none";

            // get direction
            switch (commandIDs.get(1)) {
                case 5 -> locID = curMap.getCurRoom().getNorthExitID();
                case 6 -> locID = curMap.getCurRoom().getEastExitID();
                case 7 -> locID = curMap.getCurRoom().getSouthExitID();
                case 8 -> locID = curMap.getCurRoom().getWestExitID();
            }

            if (!locID.equals("none")) {
                curMap.setCurRoom(locID);
                output = curMap.getCurRoom().getDescription();
            } else {
                cantDoThat();
            }
        }
    }

    private void look() {
        output = "You look around you.";
        if (curMap.getCurRoom().containsItems()) {
            output += "The room contains some items: \n" + listItems(curMap.getCurRoom());
        }
        output += listExits(curMap.getCurRoom());
    }

    private String listExits(Room curRoom) {
        String exits = curRoom.listExits();
        String res = "";
        if(curRoom.countExits() > 1) {
            res = "\nThe room has " + exits + " exits: \n";
        } else {
            res = "\nThe room has one exit, pointing " + exits + ".\n";
        }

        return res;
    }

    private String listItems(Room curRoom) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String itemId : curRoom.getItemIDs()) {
            sb.append( i + ": " + itemManager.getItem(itemId).toString() + "\n");
            i++;
        }
        return sb.toString();
    }

    public void cantDoThat() {
        System.out.println("Can't do that right now");
    }

    public void help() {
        output = switch (gameState) {
            case Normal -> "\nTry 'look'. If you see items when you look, try 'take [item number]'. If you see exits, try " +
                    "'go [direction]'.\n";
            default -> "\nthis is the help menu\n";
        };
    }

}
