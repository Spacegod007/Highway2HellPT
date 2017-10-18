package logic.administration;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.Gamerule;
import logic.Gamerules;
import sample.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby implements Runnable, Serializable{
    private int id;
    private User host;
    private String name;
    private List<User> players;
    private List<Gamerule> gamerules;
    private static int maxSize = 64;
    public User getHost() {
        return host;
    }
    public int getId(){return id;}

    public void setHost(User host) {
        this.host = host;
    }

    public ObservableList<User> getPlayers() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(players));
    }

    public List<Gamerule> getGamerules() {
        return Collections.unmodifiableList(gamerules);
    }

    public String getName() {
        return name;
    }
    
    public Lobby(String name){
        players = new ArrayList<>();
        gamerules = new ArrayList<>();
        this.name = name;
        this.id = 10;
    }

    public boolean join(User player){
        try
        {
            if (host == null)
            {
                System.out.println("Host set: " + player.toString());
                setHost(player);
            }
            this.players.add(player);
            System.out.println("Player added: " + player.toString());
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
        //throw new UnsupportedOperationException();
    }
    public void editGameRules(){
        throw new UnsupportedOperationException();
    }
    public void startGame(){
        throw new UnsupportedOperationException();
    }
    public void migrateHost(){
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return name + ": (" + players.size() + "/" + Lobby.maxSize + ")";
    }

    @Override
    public void run() {
        try{while(true){
           if(false){
               break;
           }
        }}
        catch(Exception e){}
    }

}
