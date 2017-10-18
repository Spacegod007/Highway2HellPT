package logic.administration;

import RMItest.LobbyAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Administration
{
    private User user;
    private List<Lobby> lobbies;
    private ObservableList<Lobby> obsLobbies;
    public ObservableList<Lobby> getLobbies()
    {
        return FXCollections.unmodifiableObservableList(obsLobbies);
    }

    public Administration(String name){
        this.user = new User(name);
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
            //lobbyAdmin.addLobby(lobby);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }




}
