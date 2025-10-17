package J4.sae_labyrinthe.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class DataConverter {
    private static final String SEP = ";";

    public static FileWriter convertToCSV(String[][] data, String path) {
        FileWriter file = null;
        try {
            file = new FileWriter(path);

            for (String[] stringTab : data) {
                for (String elt : stringTab) {
                    file.append(elt).append(SEP);
                }
                file.append("\n");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return file;
    }

    public static FileWriter convertToCSV(Map<String, String> data, String path) {
        String[][] temp = new String[][]{};
        int cpt = 0;

        for (Map.Entry<String, String> entry : data.entrySet()) {
            temp[cpt][0] = entry.getKey();
            temp[cpt][1] = entry.getValue();
        }

        return convertToCSV(temp, path);
    }
    
    public static String[][] readFromCSV(String path){
        String[][] tempTab = new String[][]{};
        BufferedReader br = null;
        String line = "";
        String[] lineSplit = null;
        int cpt = 0;

        try {
            br = new BufferedReader(new FileReader(path));

            while (br.ready()) {
                line = br.readLine();
                lineSplit = line.split(SEP);

                for (int i = 0; i<lineSplit.length; i++){
                    tempTab[cpt][i] = lineSplit[i];
                }
                cpt ++;
            }

            br.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        return tempTab;
    }
    
    public static HashMap<String, String> StringTabToMap(String[][] stringTab) throws Exception{
        if (stringTab[0].length != 2){
            // TODO : mettre une bonne exception
            throw(new Exception("mauvais nombre de colonnes"));
        }
        HashMap<String,String> temp = new HashMap<>();

        for (String[] strings : stringTab) {
            temp.put(strings[0],strings[0]);
        }

        return temp;
    }

}
