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

public class Lobby extends java.util.Observable implements Runnable, Serializable{
    private User host;

    public String getName() {
        return name;
    }

    public static ObservableList<Lobby> list(){
        return FXCollections.unmodifiableObservableList(observableList);
    }
    private String name;
    private List<User> players;
    private List<Gamerule> gamerules;
    private static int maxSize = 64;
    private static ObservableList<Lobby> observableList = FXCollections.observableList(new ArrayList<Lobby>());

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public ObservableList<User> getPlayers() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(players));
    }

    public List<Gamerule> getGamerules() {
        return Collections.unmodifiableList(gamerules);
    }

    public Lobby(String name){
        players = new ArrayList<>();
        gamerules = new ArrayList<>();
        this.name = name;
    }
    public Lobby(Main m, String name){
        this.addObserver(m);
        players = new ArrayList<>();
        gamerules = new ArrayList<>();
        this.name = name;
        observableList.add(this);
    }
    public Lobby(Main m, User host, String name) {
        this.addObserver(m);
        this.host = host;
        players = new ArrayList<>();
        gamerules = new ArrayList<>();
        this.name = name;
        observableList.add(this);
    }

    public void kickPlayer(User player){
        this.players.remove(player);
        this.setChanged();
        this.notifyObservers();
    }
    public void addPlayer(User player){
        if(host == null){
            setHost(player);
        }
        this.players.add(player);
        this.setChanged();
        this.notifyObservers();
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
