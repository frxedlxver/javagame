package controllers;

import my_utils.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameController {

    private static HashMap<String, Integer> VALId_COMMANDS;

    static {
        try {
            VALId_COMMANDS = loadValidCommands();
        } catch (FileNotFoundException e) {
            System.out.println("Error: commands.csv not found in data folder.");
        }
    }

    private final Input inp;
    private ArrayList<Integer> curInput;


    public GameController() {
        this.inp = new Input();
    }

    public static HashMap<String, Integer> loadValidCommands() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("resources/commands.csv"));
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
            return VALId_COMMANDS.get(command.toLowerCase());
        } catch (NullPointerException e) {
            // 0 will be used to identify unknown commands
            return 0;
        }
    }

    public ArrayList<Integer> getCurInput() {
        return this.curInput;
    }

    public boolean yesNo(String message) {
        return inp.yesNo(message);
    }
}