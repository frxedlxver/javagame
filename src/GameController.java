package src;

import src.my_utils.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameController {

    private static HashMap<String, Integer> VALID_COMMANDS;

    static {
        try {
            VALID_COMMANDS = loadValidCommands();
        } catch (FileNotFoundException e) {
            System.out.println("Error: commands.csv not found in data folder.");
        }
    }

    private final Input inp;
    private ArrayList<Integer> curInput;


    public GameController() {
        this.inp = new Input();
    }

    public static HashMap<String, Integer> getValidCommands() {
        return VALID_COMMANDS;
    }

    public static HashMap<String, Integer> loadValidCommands() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("res/commands.csv"));
        HashMap<String, Integer> result = new HashMap<>();

        while (sc.hasNext()) {
            String[] temp = sc.nextLine().split(",");
            result.put(temp[1], Integer.parseInt(temp[0]));
        }

        return result;
    }

    public void getNewInput() {
        String input = this.inp.getString();
        curInput = parseInput(input);
    }

    private ArrayList<Integer> parseInput(String input) {
        ArrayList<Integer> result = new ArrayList<>();
        for (String command : input.split(" ")) {
            result.add(translateCommand(command));
        }
        return result;
    }

    private static int translateCommand(String command) {
        try {
            return VALID_COMMANDS.get(command.toLowerCase());
        } catch (NullPointerException e) {
            // 0 will be used to identify unknown commands
            return 0;
        }
    }

    public boolean hasComplexInput() {
        return curInput.size() > 1;
    }

    public ArrayList<Integer> getCurInput() {
        return this.curInput;
    }

    public String getString(String message) {
        return inp.getString(message);
    }

    public int getInt(String message) {
        return inp.getInt(message);
    }

    public boolean yesNo(String message) {
        return inp.yesNo(message);
    }

    public int getChoice(String message, int minChoice, int maxChoice) {
        return inp.getChoice(message, minChoice, maxChoice);
    }
}