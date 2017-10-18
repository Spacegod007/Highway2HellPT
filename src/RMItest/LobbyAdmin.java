package RMItest;

import logic.administration.Lobby;
import sample.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LobbyAdmin extends UnicastRemoteObject implements ILobbyAdmin{
    private ArrayList<Lobby> Lobbys;

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

    public Lobby addLobby() throws RemoteException {
        Lobby Lobby = new Lobby("Testlobby");
        Lobbys.add(Lobby);
        System.out.println("LobbyAdmin: Lobby " + Lobby.toString() + " added to Lobby administration");
        return Lobby;
    }
}
