package RMItest;

import logic.administration.Lobby;
import logic.administration.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LobbyAdmin extends UnicastRemoteObject implements ILobbyAdmin{
    private ArrayList<Lobby> Lobbys;
    private static int nextID = 0;
    public static int getNextID(){
        int i = nextID;
        nextID++;
        return i;
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

    public Lobby addLobby(String name) throws RemoteException {
        Lobby lobby = new Lobby(name, getNextID());
        Lobbys.add(lobby);
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
