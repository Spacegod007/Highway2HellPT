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

    public User getUser() {
        return user;
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
            if(rmiClient.joinLobby(lobby))
            {
                rmiClient.setActiveLobby(lobby);
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
        try{
            Lobby lobby = rmiClient.getActiveLobby();

            if(lobby != null)
            {
                if(rmiClient.leaveLobby(lobby))
                {
                    System.out.println("Setting to null->");
                    rmiClient.setActiveLobby(null);
                    System.out.println("Success(Admin)");
                    return true;
                }
            }
            else{
                System.out.println("Lol lobby is null fucker");
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
            joinLobby(rmiClient.addLobby(name));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }




}
