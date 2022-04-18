package src.my_utils;

import java.io.Closeable;
import java.util.Scanner;

public class Input implements Closeable {

    private final Scanner sc;

    public Input() {
        this.sc = new Scanner(System.in);
    }

    public String getString(String message) {
        TimedPrint.print(message);
        return sc.nextLine();
    }

    public String getString() {
        return this.sc.nextLine();
    }

    public int getInt(String message) {

        String result = this.getString(message);

        while (!Validator.isInt(result)) {
            System.out.println("Expected input of type int.");
            result = this.getString(message);
            System.out.println("Expected input of type int.");
        }

        return Integer.parseInt(result);
    }

    public boolean yesNo(String message) {

        String result = this.getString(message);

        while (!(result.equalsIgnoreCase("Y") || result.equalsIgnoreCase("n"))){
            System.out.println("Please enter only Y or N: ");
            result = this.getString(message);
        }

        return result.equalsIgnoreCase("Y");
    }

    public int getChoice(String message, int minChoice, int maxChoice) {
        int result = getInt(message);

        while ((result < minChoice) || (result > maxChoice)) {
            result = getInt("Input must be between " + minChoice +  " and " + maxChoice + ": ");
        }

        return result;
    }

    public void close() {
        this.sc.close();
    }
}