package logic.administration;

import RMItest.RMIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Administration
{

    private RMIClient rmiClient;
    private User user;

    public Administration(RMIClient rmiClient){
        this.rmiClient = rmiClient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean kickPlayer(int l, int index) {
        try
        {
            if(rmiClient.kickPlayer(l, index))
            {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean joinLobby(Lobby lobby){
        try
        {
            if(rmiClient.joinLobby(lobby, user))
            {
               return true;
            }
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Lobby> refresh()
    {
        try{
            return FXCollections.observableList(rmiClient.getLobbies());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean hostLobby(String name)
    {
        try{
            //Lobby lobby = new Lobby(name, rmiClient.getNextID());
            joinLobby(rmiClient.addLobby(name));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }




}
