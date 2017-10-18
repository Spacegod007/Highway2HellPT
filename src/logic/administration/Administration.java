package logic.administration;

import RMItest.RMIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Administration
{

    private RMIClient rmiClient;
    private User user;
    private List<Lobby> lobbies;
    private ObservableList<Lobby> obsLobbies;
    public ObservableList<Lobby> getLobbies()
    {
        return FXCollections.unmodifiableObservableList(obsLobbies);
    }

    public Administration(RMIClient rmiClient){
        this.rmiClient = rmiClient;
        lobbies = new ArrayList<>();
        obsLobbies = FXCollections.observableList(lobbies);
        //**
        obsLobbies.add(new Lobby("MOCKUP"));
        //**
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean joinLobby(Lobby lobby){
        try
        {
            if(lobby.join(user))
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

    public boolean hostLobby(String name)
    {
        try{
            Lobby lobby = new Lobby(name);
            joinLobby(lobby);
            obsLobbies.add(lobby);
            rmiClient.addLobby(lobby);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }




}
