package logic.game;

import javafx.scene.paint.Color;
import logic.Gamerule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game implements Runnable, Observer {
    private List<GameObject> GameObjects;
    private List<Gamerule> gamerules;
    private double scrollSpeed = 2;
    private int obstacleCount = 2;

    public Game(List<Gamerule> gamerules) {
        this.gamerules = gamerules;
        GameObjects = new ArrayList<>();
        GameObjects.add(new PlayerObject(new Point(960, 900),"Player1", Color.BLACK));
        GameObjects.add(new PlayerObject(new Point(860, 900),"Player2", Color.BLACK));

        for(int i=0; i<obstacleCount; i++){
            GameObjects.add(new ObstacleObject(70, 48));
            System.out.println("item " + i + " added");
        }
    }

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

    public void update(){
        int index = 0;
        //Method for scrolling the screen.
        for(GameObject GO : getGameObjects())
        {
            //GO scroll.
            GO.setAnchor(new Point(GO.getAnchor().getX(), GO.getAnchor().getY() + scrollSpeed));

            //Check if GO is dead.
            //Game window: 1200x1000
            //Character size 52x36
            if(GO.getClass() == PlayerObject.class)
            {
                //Setting the borders of the map for player death.
                //Might need some tweaking, leave to the tester.
                PlayerObject PO = (PlayerObject)GO;
                if(PO.getAnchor().getX() < 0 + PO.getPlayerSize()[0])
                {
                    PO.setIsDead(true);
                }
                else if(PO.getAnchor().getX() > 1200)
                {
                    PO.setIsDead(true);
                }
                else if(PO.getAnchor().getY() + (PO.getPlayerSize()[1]/2) > 1000)
                {
                    PO.setIsDead(true);
                }
                else if(PO.getAnchor().getY() + (PO.getPlayerSize()[1]/2) < 0)
                {
                    PO.setIsDead(true);
                }
            }

            if (GO.getClass() == ObstacleObject.class)
            {
                ObstacleObject OO = (ObstacleObject) GO;
                if(OO.getAnchor().getY() + (OO.getHeight()) > 1000)
                {
                    GameObjects.set(index, new ObstacleObject(70, 48));
                    System.out.println("Obstacle respawned");
                }
            }
            index++;
        }
        index = 0;
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
    }

    public PlayerObject moveCharacter(String playerName, Direction direction) {
        for (GameObject g : GameObjects) {
            if (g.getClass() == PlayerObject.class)
            {
                PlayerObject p = (PlayerObject) g;
                if (playerName == "Player1" && p.getName() == "Player1")
                {
                    switch (direction) {
                        case LEFT:
                            p.move(Direction.LEFT);
                            break;
                        case RIGHT:
                            p.move(Direction.RIGHT);
                            break;
                    }
                    return p;
                }
                if (playerName == "Player2" && p.getName() == "Player2")
                {
                    switch (direction) {
                        case A:
                            p.move(Direction.LEFT);
                            break;
                        case D:
                            p.move(Direction.RIGHT);
                            break;
                    }
                    return p;
                }
            }
        }
        return null;
    }

    public ArrayList<ObstacleObject> returnObstacleObjects()
    {
        ArrayList<ObstacleObject> listToReturn = new ArrayList<>();
        for (GameObject GO : GameObjects)
        {
            if (GO.getClass() == ObstacleObject.class)
            {
                listToReturn.add((ObstacleObject)GO);
            }
        }
        return listToReturn;
    }
}
