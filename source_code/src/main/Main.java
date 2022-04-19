package main;

import my_utils.Input;

public class Main {
    public static void main(String[] args) {
        Input inp = new Input();
        Game g = Game.init(inp);
        g.gameLoop();
    }
}
