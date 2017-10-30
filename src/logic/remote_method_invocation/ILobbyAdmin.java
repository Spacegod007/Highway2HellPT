package logic.remote_method_invocation;

import logic.administration.Lobby;
import logic.administration.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILobbyAdmin extends Remote {
    int getNumberOfLobbies() throws RemoteException;
    Lobby getActiveLobby(User user) throws RemoteException;
    Lobby addLobby(String lobby) throws RemoteException;
    List<Lobby> getLobbies() throws RemoteException;
    boolean joinLobby(Lobby lobby, User user) throws RemoteException;
    boolean kickPlayer(int l, int index) throws RemoteException;
    boolean leaveLobby(Lobby lobby, User user) throws RemoteException;
    Lobby setActiveLobby(User user, Lobby lobby) throws RemoteException;
    User addUser(String name) throws RemoteException;
}
