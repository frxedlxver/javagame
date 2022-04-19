package my_utils;

public class Help {
    public static String mainMenu() {
        return """
                    You will interact with this game by entering commands into the console.
                    The list of commands currently accepted is not exhaustive.
                    To help with this, you can enter '?' at any time to receive an explanation of the current game state.
                    This will include suggestions about current valid commands, and how to use them.
                    """;
    }

    public static String normal() {
        return """
                Try 'look'. If you see items when you look, try 'take [item number]'. If you see exits, try 'go [direction]'.
                """;
    }

    public static String combat() {
        return """
                Use 'attack' or 'block' in combination with 'left' or 'right' to attack or block with right- or left-hand equipment.
                Use 'item' to access consumable inventory.
                """;

    }

    public static String inventory() {
        return "";
    }
}
