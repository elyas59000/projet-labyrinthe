package J4.sae_labyrinthe.modele;

import java.util.HashMap;

import J4.sae_labyrinthe.utils.DataConverter;

public class Connection {
    HashMap<String,String> loginMdp = null;


    public HashMap<String,String> getLoginMdp(String path){
        HashMap<String,String> temp = null;
        try{
            temp = DataConverter.StringTabToMap(DataConverter.readFromCSV(path));
        }catch (Exception e){
            System.err.println(e);
        }
        return temp;
    }
    
}
