package J4.sae_labyrinthe.modele;

import java.util.HashMap;

import J4.sae_labyrinthe.utils.DataConverter;

public class Connection {
    private final String LOGINPATH = "src\\main\\ressources\\loginMdp.txt";
    private HashMap<String,String> loginMdp = getLoginMdp(LOGINPATH);
    private boolean connected = false;

    // constructeur par default

    public HashMap<String,String> getLoginMdp(String path){
        HashMap<String,String> temp = null;
        try{
            temp = DataConverter.StringTabToMap(DataConverter.readFromCSV(path));
        }catch (Exception e){
            System.err.println(e);
        }
        return temp;
    }

    public boolean isConnected(){
        return connected;
    }

    public void setConnected(boolean connected){
        this.connected = connected;
    }

    public boolean containAuth(String login, String mdp){
        return loginMdp.containsKey(login) && loginMdp.containsValue(mdp);
    }

    public void connection(String login, String mdp){
        if (!isConnected() && containAuth(login, mdp)){
            setConnected(true);
        }
    }


}
