package my_utils;

import main.Game;

import java.io.*;

public class SaveData {

    private static final String SAVE_PATH = "data/saveData.bin";

    public static Game load() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH));
        Game g = (Game) ois.readObject();
        ois.close();
        return g;
    }

    public static String save(Game g) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH));
            oos.writeObject(g);
            oos.close();
            return "\nmain.Game saved.\n";
        } catch (IOException e) {
            return "\nmain.Game cannot be saved\n\n";
        }
    }
}
