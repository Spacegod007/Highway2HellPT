package logic.administration;

import logic.remote_method_invocation.RMIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Administration
{

    private RMIClient rmiClient;
    private User user;

    public Administration(RMIClient rmiClient){
        this.rmiClient = rmiClient;
        this.user = rmiClient.getUser();
    }


    public boolean inLobby()
    {
       if(rmiClient.getActiveLobby() != null)
       {
           return true;
       }
       return false;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public boolean joinLobby(Lobby lobby){
        try
        {
            if(rmiClient.joinLobby(lobby))
            {
                rmiClient.setActiveLobby(lobby, rmiClient.getUser().getID());
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

    public boolean leaveLobby()
    {
        return leaveLobby(rmiClient.getUser().getID());
    }
    public boolean leaveLobby(int leaverId)
    {
        try{
            Lobby lobby = rmiClient.getActiveLobby();

            if(lobby != null)
            {
                if(rmiClient.leaveLobby(lobby.getId(), leaverId))
                {
                    return true;
                }
            }
            else{
                System.out.println("Error: Lobby is null");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean hostLobby(String name)
    {
        try{
            Lobby lobby = rmiClient.addLobby(name);
            if(lobby != null)
            {
                rmiClient.setActiveLobby(lobby, rmiClient.getUser().getID());
                return true;
            }
            return false;

        }
        catch(Exception e){
            return false;
        }
    }




}
