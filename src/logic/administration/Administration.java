package logic.administration;

import sample.Main;

import javax.print.DocFlavor;
import java.util.List;

public class Administration {
    private User user;
    private Main m;
    private Lobby l;

    public Lobby getLobby(){return l;}

    public Administration(Main m, String n) {
        this.m = m;
        this.user = new User(n);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean joinLobby(Lobby l){
        try
        {
            l.addPlayer(user);
            this.l = l;
            return true;
        }
        catch(Exception e)
        {
            //return false;
            throw new UnsupportedOperationException();
        }
    }

    public void kickPlayer(User p){
        l.kickPlayer(p);
    }

    //user moet nog geSet worden voor dit uitgevoerd kan worden
    public boolean hostLobby()
    {
       try {
           Lobby lobby = new Lobby(m, "new");
           joinLobby(lobby);
           Thread server = (new Thread(lobby)); //
           server.start();
           l = lobby;
           return true;
       }
       catch(Exception e) {
           //return false;
           throw new UnsupportedOperationException();
       }
    }



}
