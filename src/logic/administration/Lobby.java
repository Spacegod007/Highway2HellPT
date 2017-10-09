package logic.administration;

import logic.Gamerule;
import logic.Gamerules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby {
    private User host;
    private List<User> players;
    private List<Gamerule> gamerules;

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public List<User> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Gamerule> getGamerules() {
        return Collections.unmodifiableList(gamerules);
    }

    public Lobby(User host) {
        this.host = host;
        players = new ArrayList<>();
        gamerules = new ArrayList<>();
    }

    public void addPlayer(){
        throw new UnsupportedOperationException();
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
}
