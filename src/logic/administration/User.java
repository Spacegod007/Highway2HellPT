package logic.administration;

import java.io.Serializable;

public class User implements Serializable
{
    private String username;
    private int idInLobby;
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

    public User(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
