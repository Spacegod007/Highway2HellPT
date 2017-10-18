package RMItest;

import logic.administration.Lobby;
import logic.administration.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILobbyAdmin extends Remote {
    int getNumberOfLobbies() throws RemoteException;
    Lobby getLobby(int nr) throws RemoteException;
    Lobby addLobby(String lobby) throws RemoteException;
    List<Lobby> getLobbies() throws RemoteException;
    boolean joinLobby(Lobby lobby, User user) throws RemoteException;
}
