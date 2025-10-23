package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.utils.DataConverter;
import org.junit.jupiter.api.Test;

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

}
