package logic.administration;


import java.io.Serializable;

public class User implements Serializable
{
    private String username;
    private int ID;
    private int idInLobby;
    private Lobby activeLobby;

    public int getID()
    {
        return ID;
    }
    public Lobby getActiveLobby()
    {
        return activeLobby;
    }

    public void setActiveLobby(Lobby activeLobby)
    {
        this.activeLobby = activeLobby;
    }

    public int getIdInLobby()
    {
        return idInLobby;
    };
    public void setIdInLobby(int i)
    {
        this.idInLobby = i;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, int ID) {
        this.username = username;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return username;
    }
}
