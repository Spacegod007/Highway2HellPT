package logic.game;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import logic.Gamerule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game implements Runnable, Observer {
    private List<GameObject> GameObjects;
    private List<Gamerule> gamerules;
    private double scrollSpeed = 1.5;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public Game(List<Gamerule> gamerules) {
        this.gamerules = gamerules;
        GameObjects = new ArrayList<>();
        GameObjects.add(new PlayerObject(new Point(900, 900),"Player1", Color.BLACK));
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
        }
    }

    public Point moveCharacter(String playerName, Direction direction) {
        for (GameObject g : GameObjects) {
            if (g.getClass() == PlayerObject.class) ;
            {
                PlayerObject p = (PlayerObject) g;
                if (p.getName() == "Player1") {
                    switch (direction) {
                        case LEFT:
                            p.move(Direction.LEFT);
                            System.out.println("Player moved left");
                            break;
                        case RIGHT:
                            p.move(Direction.RIGHT);
                            System.out.println("Player moved right");
                            break;
                    }
                }
                if (p.getName() == "Player2") {
                    switch (direction) {
                        case A:
                            p.move(Direction.LEFT);
                            System.out.println("Player moved left");
                            break;
                        case D:
                            p.move(Direction.RIGHT);
                            System.out.println("Player moved right");
                            break;
                    }
                }
                    return p.getAnchor();
            }
        }
        return null;
    }
}
