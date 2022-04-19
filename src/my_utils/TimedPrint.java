package src.my_utils;

public class TimedPrint {

    public static void print(String input)  {
        int t = 30; // default 30
        int tComma = 150; // '' 150
        int tPeriod = 350; // '' 350
        int tNewline = 500; // '' 500
        int tBetween = 200; // '' 200

        try {
            System.out.println();
            Thread.sleep(tBetween);
            for (Character c : input.toCharArray()) {
                switch (c) {
                    case '\n' -> {
                        Thread.sleep(tNewline);
                        System.out.print(c);
                    }
                    case '.' -> {
                        System.out.print(c);
                        Thread.sleep(tPeriod);
                    }
                    case ',' -> {
                        System.out.print(c);
                        Thread.sleep(tComma);
                    }
                    case '_' -> {
                        Thread.sleep(tPeriod);
                    }
                    default -> {
                        System.out.print(c);
                        Thread.sleep(t);
                    }
                }
            }
            Thread.sleep(tBetween);
            System.out.println();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
