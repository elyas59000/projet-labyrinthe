package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.utils.DataConverter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataConverter {

    @Test
    void testStringTabToMap() throws Exception {
        String[][] data = {{"utilisateur1","mdp"},{"utilisateur2","mdp"}};
        HashMap<String,String> map = DataConverter.StringTabToMap(data);
        assertEquals(2,map.size());
        assertTrue(map.containsKey("utilisateur1"));
        assertEquals("mdp",map.get("utilisateur1"));
    }

    @Test
    void testReadFromCSV() {


        String cheminFichier = "src/main/test/J4/sae_labyrinthe/modele/test.csv";

        String[][] dataAttendues = {
                {"user1", "pass1"},
                {"user2", "pass2"}
        };

        String[][] dataLues = DataConverter.readFromCSV(cheminFichier);

        assertNotNull(dataLues);
        assertEquals(dataAttendues.length, dataLues.length);
        assertArrayEquals(dataAttendues[0], dataLues[0]);
        assertArrayEquals(dataAttendues[1], dataLues[1]);
    }


}
