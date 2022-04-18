package src.my_utils;

public class Validator {
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public static boolean containsLetters(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
}