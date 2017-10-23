package RMItest;

import logic.administration.Lobby;
import logic.administration.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LobbyAdmin extends UnicastRemoteObject implements ILobbyAdmin{
    private ArrayList<Lobby> Lobbys;
    private ArrayList<User> Users;
    private static int nextID = 0;
    private static int nextUserID = 0;
    static int getNextID(){
        int i = nextID;
        nextID++;
        return i;
    }
    static int getNextUserID(){
        int i = nextUserID;
        nextUserID++;
        return i;
    }

    // Constructor
    public LobbyAdmin() throws RemoteException {
        Lobbys = new ArrayList<>();
        Users = new ArrayList<>();
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

    public boolean leaveLobby(Lobby lobby, User user)
    {
        for(Lobby l : Lobbys)
        {
            if(l.getId() == lobby.getId()){
                return l.leave(user);
            }
        }
        return false;
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

    public boolean kickPlayer(int l, int index)
    {
        return (Lobbys.get(l)).kickPlayer(index);
    }

    public Lobby setActiveLobby(User user, Lobby lobby)
    {
        if(lobby != null)
        {
            for(Lobby l : Lobbys){
                if(l.getId() == lobby.getId())
                {  for(User u : Users){
                    if(u.getID() == user.getID())
                    {
                        u.setActiveLobby(l);
                    }
                }
                }
            }
        }
        else{
            for(User u : Users){
                if(u.getID() == user.getID())
                {
                    u.setActiveLobby(null);
                }
            }
        }
        return getActiveLobby(user);
    }

    public User addUser(String username)
    {
        User user = new User(username, getNextUserID());
        Users.add(user);
        return user;
    }

    public Lobby getActiveLobby(User user)
    {
        for(User u : Users)
        {
            if(u.getID() == user.getID())
            {
                return u.getActiveLobby();
            }
        }
        return null;
    }
}
