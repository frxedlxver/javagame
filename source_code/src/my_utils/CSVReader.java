package my_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CSVReader {

    public static HashMap<String, ArrayList<String>> buildDataArray(String filePath) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, ArrayList<String>> result = new HashMap<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            // skip over header rows
            if (!line.contains("HEAD")) {
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(line.split(",")));

                result.put(temp.get(0), temp);
            }
        }
        return result;
    }

}
