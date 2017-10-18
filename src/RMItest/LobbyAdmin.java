package RMItest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.administration.Lobby;
import logic.administration.User;
import sample.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LobbyAdmin extends UnicastRemoteObject implements ILobbyAdmin{
    private ArrayList<Lobby> Lobbys;
    private static int nextID = 100;
    public static int getNextID(){
        nextID++;
        return nextID;
    }

    // Constructor
    public LobbyAdmin() throws RemoteException {
        Lobbys = new ArrayList<Lobby>();
    }

    public int getNumberOfLobbies() throws RemoteException {
        System.out.println("LobbyAdmin: Request for number of Lobbies");
        return Lobbys.size();
    }

    public Lobby getLobby(int nr) throws RemoteException {
        System.out.println("LobbyAdmin: Request for Lobby with number " + nr);
        if (nr >= 0 && nr < Lobbys.size()) {
            return Lobbys.get(nr);
        }
        else {
            return null;
        }
    }

    public Lobby addLobby(Lobby lobby) throws RemoteException {
        Lobbys.add(lobby);
        nextID++;
        System.out.println("LobbyAdmin: Lobby " + lobby.toString() + " added to Lobby administration");
        return lobby;
    }

    public List<Lobby> getLobbies() throws RemoteException
    {
        return Lobbys;
    }

    public boolean joinLobby(Lobby lobby, User user)
    {
        for(Lobby l : Lobbys)
        {
            if(l.getId() == lobby.getId()){
                return l.join(user);
            }
        }
        return false;
    }
}
