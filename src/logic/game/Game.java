package logic.game;

import logic.Gamerule;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game implements Runnable, Observer {
    private List<GameObject> GameObjects;
    private List<Gamerule> gamerules;

    public List<GameObject> getGameObjects() {
        return GameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        GameObjects = gameObjects;
    }

    public List<Gamerule> getGamerules() {
        return gamerules;
    }

    public void setGamerules(List<Gamerule> gamerules) {
        this.gamerules = gamerules;
    }

    public Game(List<Gamerule> gamerules) {
        this.gamerules = gamerules;
    }

    public void update(){
        throw new UnsupportedOperationException();
    }
    public void convertAccountsToPlayerObjects(){
        throw new UnsupportedOperationException();
    }
    public void endGame(){
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException();
    }
}
