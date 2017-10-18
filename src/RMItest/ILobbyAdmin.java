package RMItest;

import logic.administration.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILobbyAdmin extends Remote {
    public int getNumberOfLobbies() throws RemoteException;
    public Lobby getLobby(int nr) throws RemoteException;
    public Lobby addLobby(Lobby lobby) throws RemoteException;
}
